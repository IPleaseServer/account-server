package site.iplease.accountserver.domain.register.policy

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.data.type.AuthType
import site.iplease.accountserver.global.auth.exception.WrongEmailTokenException
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder
import site.iplease.accountserver.global.common.exception.PolicyViolationException
import site.iplease.accountserver.global.common.repository.AccountRepository
import site.iplease.accountserver.global.common.type.PolicyType

@Component
class StudentRegistrationPolicyImpl(
    private val authTokenDecoder: AuthTokenDecoder,
    private val accountRepository: AccountRepository
): StudentRegistrationPolicy {
    override fun validate(validationDto: StudentRegisterValidationDto): Mono<Unit> =
        validateEmailToken(validationDto.emailToken)
            .flatMap { validateStudentNumber(validationDto.studentNumber) }

    private fun validateEmailToken(emailToken: String): Mono<Unit> =
        authTokenDecoder.decode(AuthTokenDto(emailToken)) //1. 토큰이 이메일토큰인지 검증한다.
            .flatMap {
                if(it.type == AuthType.EMAIL) it.data.toMono()
                else Mono.error(WrongEmailTokenException("해당 토큰은 ${it.type}토큰입니다!"))
            }.flatMap { email -> accountRepository.existsByEmail(email) } //2. (1)에서 추출한 이메일이 이미 가입된 계정의 이메일인지 검증한다.
            .flatMap { isExists ->
                if(isExists) Mono.error(PolicyViolationException(PolicyType.REGISTER_EMAIL, "이미 해당 메일로 가입한 계정이 존재합니다."))
                else Unit.toMono()
            }

    private fun validateStudentNumber(studentNumber: Int): Mono<Unit> =
        accountRepository.existsByStudentNumber(studentNumber) //1. 이미 가입된 계정의 학번인지 검증한다.
            .flatMap {
                if(it) Mono.error(PolicyViolationException(PolicyType.REGISTER_STUDENT_NUMBER, "이미 해당 학번을 가진 계정이 존재합니다."))
                else Unit.toMono()
            }
}