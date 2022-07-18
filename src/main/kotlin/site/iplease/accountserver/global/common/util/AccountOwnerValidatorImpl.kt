package site.iplease.accountserver.global.common.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.global.auth.exception.WrongEmailTokenException
import site.iplease.accountserver.global.common.repository.AccountRepository

@Component
class AccountOwnerValidatorImpl(
    private val accountRepository: AccountRepository
): AccountOwnerValidator {
    override fun validate(accountId: Long, email: String): Mono<Unit> =
        accountRepository.findByEmail(email)
            .flatMap { account ->
                if (account.id == accountId) Unit.toMono()
                else Mono.error(WrongEmailTokenException("해당 토큰은 다른 사용자의 인증토큰입니다! - ${account.id}"))
            }
}