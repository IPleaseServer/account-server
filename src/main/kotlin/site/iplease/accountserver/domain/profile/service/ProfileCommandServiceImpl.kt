package site.iplease.accountserver.domain.profile.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.util.ProfileCommandPolicyValidator
import site.iplease.accountserver.global.common.repository.AccountRepository

@Service
class ProfileCommandServiceImpl(
    private val profileCommandPolicyValidator: ProfileCommandPolicyValidator,
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
): ProfileCommandService {
    override fun changePassword(accountId: Long, emailToken: String, newPassword: String): Mono<Unit> =
        profileCommandPolicyValidator.validateChangePassword(accountId, emailToken, newPassword)
            .map { account -> account.copy(encodedPassword = passwordEncoder.encode(newPassword)) }
            .flatMap { account -> accountRepository.save(account) }
            .map{ }
}