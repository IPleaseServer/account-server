package site.iplease.accountserver.domain.profile.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.data.type.AuthType
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder
import site.iplease.accountserver.global.common.entity.Account
import site.iplease.accountserver.global.common.repository.AccountRepository
import site.iplease.accountserver.global.auth.exception.WrongEmailTokenException

@Component
class ProfileCommandPolicyValidatorImpl(
    private val authTokenDecoder: AuthTokenDecoder,
    private val accountRepository: AccountRepository,
): ProfileCommandPolicyValidator {
    override fun validateChangePassword(accountId: Long, emailToken: String, newPassword: String): Mono<Account> =
        authTokenDecoder.decode(AuthTokenDto(token = emailToken))
            .flatMap { dto ->
                if (dto.type == AuthType.EMAIL) dto.data.toMono()
                else Mono.error(WrongEmailTokenException("해당 토큰은 ${dto.type}토큰입니다!"))
            }.flatMap { email -> accountRepository.findByEmail(email) }
            .flatMap { account ->
                if (account.id == accountId) account.toMono()
                else Mono.error(WrongEmailTokenException("해당 토큰은 다른 사용자의 인증토큰입니다! - ${account.id}"))
            }
}