package site.iplease.accountserver.domain.auth.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.domain.auth.data.type.AuthType
import site.iplease.accountserver.domain.auth.entity.AuthCode
import site.iplease.accountserver.domain.auth.config.AuthProperties
import site.iplease.accountserver.domain.auth.repository.AuthCodeRepository
import site.iplease.accountserver.domain.auth.util.AuthCodeGenerator
import site.iplease.accountserver.domain.auth.util.AuthTokenEncoder
import site.iplease.accountserver.infra.email.service.EmailService

@Service
class AuthorizeServiceImpl(
    private val authProperties: AuthProperties,
    private val authCodeGenerator: AuthCodeGenerator,
    private val authCodeRepository: AuthCodeRepository,
    private val authTokenEncoder: AuthTokenEncoder,
    private val emailService: EmailService,
): AuthorizeService {
    private val emailProperty by lazy{ authProperties.emailProperties }

    override fun authorize(code: String): Mono<AuthTokenDto> =
        authCodeRepository.select(code) //DataStore에서 인증코드에 매핑된 Type(인증대상 종류 := Class)과 Data(인증대상 정보 := Instance)를 가져온다.
            .flatMap { encodeAuthToken(it.type, it.data) }//가져온 Type과 Data로 인증토큰을 생성하고, 반환한다.


    private fun encodeAuthToken(type: AuthType, data: String): Mono<AuthTokenDto> =
        authTokenEncoder.encodeAuthToken(type, data)//Type과 Data로 인증토큰을 생성한다.

    override fun authorizeEmail(email: String): Mono<Unit> =
        authCodeGenerator.generate()//인증코드를 발급한다.
            .flatMap { sendAuthEmail(email, it) }//인증코드를 이메일로 보낸다.
            .flatMap { saveAuthData(AuthType.EMAIL, email, it) }//인증코드와 이메일을 매핑하여 DataStore에 저장한다.
            .map {  }//Payload가 비어있는 ResponseEntity(Status=OK)를 반환한다.


    private fun sendAuthEmail(email: String, authCode: String): Mono<String> =
        emailService.sendEmail(//인증메일을 보낸다.
            to = email,
            title = emailProperty.emailTitle,
            template = emailProperty.templatePath,
            context = mapOf("code" to authCode)
        ).map { authCode }//인증코드를 반환한다.

    private fun saveAuthData(type: AuthType, data: String, code: String): Mono<String> =
        authCodeRepository.insert(AuthCode(//인증정보를 저장한다.
            code = code,
            type = type,
            data = data))
        .then(code.toMono())//인증코드를 반환한다.
}