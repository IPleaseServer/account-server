package site.iplease.accountserver.domain.register.util.atomic

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.global.common.type.PolicyType
import site.iplease.accountserver.global.common.exception.PolicyViolationException
import site.iplease.accountserver.global.common.repository.AccountRepository

@Component
class RegisterStudentNumberValidatorImpl(
    private val accountRepository: AccountRepository
): RegisterStudentNumberValidator {
    override fun valid(studentNumber: Int): Mono<Unit> =
        accountRepository.existsByStudentNumber(studentNumber)
            .flatMap {
                if(it) Mono.error(PolicyViolationException(PolicyType.REGISTER_STUDENT_NUMBER, "이미 해당 학번을 가진 계정이 존재합니다."))
                else Unit.toMono()
            }
}