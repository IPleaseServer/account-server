package site.iplease.accountserver.domain.register.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.register.data.type.PolicyType
import site.iplease.accountserver.domain.register.exception.PolicyViolationException
import site.iplease.accountserver.domain.register.repository.AccountRepository

@Component
class RegisterEmailCheckerImpl(
    private val accountRepository: AccountRepository
): RegisterEmailChecker {
    override fun valid(email: String): Mono<Unit> =
        accountRepository.existsByEmail(email)
            .flatMap {
                if(it) Mono.error(PolicyViolationException(PolicyType.REGISTER_EMAIL, "이미 해당 메일로 가입한 계정이 존재합니다."))
                else Unit.toMono()
            }
}