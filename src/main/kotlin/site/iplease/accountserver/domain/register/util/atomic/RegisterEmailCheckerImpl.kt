package site.iplease.accountserver.domain.register.util.atomic

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.global.common.type.PolicyType
import site.iplease.accountserver.global.common.exception.PolicyViolationException
import site.iplease.accountserver.global.register.repository.AccountRepository

@Component
class RegisterEmailCheckerImpl(
    private val accountRepository: AccountRepository
): RegisterEmailChecker {
    override fun valid(email: String): Mono<Unit> =
        isAlreadyRegistered(email)
            .flatMap { isNotSchoolEmail(email) }


    private fun isAlreadyRegistered(email: String) =
        accountRepository.existsByEmail(email)
            .flatMap {
                if(it) Mono.error(PolicyViolationException(PolicyType.REGISTER_EMAIL, "이미 해당 메일로 가입한 계정이 존재합니다."))
                else Unit.toMono()
            }

    private fun isNotSchoolEmail(email: String) =
        email.toMono()
            .flatMap {
                if(email.matches(Regex("^.+@gsm.hs.kr\$"))) Unit.toMono()
                else Mono.error(PolicyViolationException(PolicyType.REGISTER_EMAIL, "학교이메일이 아닙니다."))
            }
}