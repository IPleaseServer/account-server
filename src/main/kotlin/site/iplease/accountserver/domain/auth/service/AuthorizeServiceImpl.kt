package site.iplease.accountserver.domain.auth.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.domain.auth.data.type.AuthType
import site.iplease.accountserver.domain.auth.data.entity.AuthCode
import site.iplease.accountserver.domain.auth.config.AuthProperties
import site.iplease.accountserver.domain.auth.data.dto.AuthDto
import site.iplease.accountserver.domain.auth.repository.AuthCodeRepository
import site.iplease.accountserver.domain.auth.util.atomic.AuthCodeGenerator
import site.iplease.accountserver.domain.auth.util.atomic.AuthTokenEncoder
import site.iplease.accountserver.infra.email.service.EmailService
import site.iplease.accountserver.infra.teacher_code.service.TeacherAuthorizerService

@Service
class AuthorizeServiceImpl(
    private val authProperties: AuthProperties,
    private val authCodeGenerator: AuthCodeGenerator,
    private val authCodeRepository: AuthCodeRepository,
    private val authTokenEncoder: AuthTokenEncoder,
    private val emailService: EmailService,
    private val teacherAuthorizerService: TeacherAuthorizerService
): AuthorizeService {
    private val emailProperty by lazy{ authProperties.emailProperties }

    ///BusinessLogics and Endpoints
    override fun authorize(code: String): Mono<AuthTokenDto> =
        authCodeRepository.select(code) //DataStore에서 인증코드에 매핑된 Type(인증대상 종류 := Class)과 Data(인증대상 정보 := Instance)를 가져온다.
            .flatMap { deleteAuthData(it) }//DataStore에서 인증코드 매핑정보를 제거한다.
            .flatMap { encodeAuthToken(AuthDto(it.type, it.data)) }//가져온 Type과 Data로 인증토큰을 생성하고, 반환한다.

    private fun encodeAuthToken(dto: AuthDto): Mono<AuthTokenDto> =
        authTokenEncoder.encode(dto)//Type과 Data로 인증토큰을 생성한다.

    override fun authorizeEmail(email: String): Mono<Unit> =
        authCodeGenerator.generate()//인증코드를 발급한다.
            .flatMap { sendAuthEmail(email, it) }//인증코드를 이메일로 보낸다.
            .map { AuthCode(it, AuthType.EMAIL, email) }//인증코드와 이메일을 매핑한다.
            .flatMap { saveAuthData(it) }//매핑한 데이터를 DataStore에 저장한다.
            .map {  }

    override fun authorizeTeacher(identifier: String): Mono<Unit> =
        authCodeGenerator.generate()//인증코드를 발급한다.
            .flatMap { sendAuthIdentifier(identifier, it) }//인증코드를 identifier로 교사인증기에 보낸다.
            .map { AuthCode(it, AuthType.TEACHER, identifier) }//identifier와 인증코드를 매핑한다.
            .flatMap { saveAuthData(it) }//매핑한 데이터를 DataStore에 저장한다.
            .map {  }


    private fun sendAuthIdentifier(identifier: String, authCode: String): Mono<String> =
        teacherAuthorizerService.sendData(
            key = identifier,
            value = authCode
        ).map { authCode }

    private fun sendAuthEmail(email: String, authCode: String): Mono<String> =
        emailService.sendEmail(//인증메일을 보낸다.
            to = email,
            title = emailProperty.emailTitle,
            template = emailProperty.templatePath,
            context = mapOf("code" to authCode)
        ).map { authCode }//인증코드를 반환한다.

    ///Wrap Commands of AuthData to Private method
    private fun saveAuthData(entity: AuthCode): Mono<String> =
        authCodeRepository.insert(entity)//인증정보를 저장한다.
            .then(entity.code.toMono())//인증코드를 반환한다.

    private fun deleteAuthData(entity: AuthCode) =
        authCodeRepository.delete(code = entity.code)//인증정보를 제거한다.
            .then(entity.toMono())//인증코드를 반환한다.
}