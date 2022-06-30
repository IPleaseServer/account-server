package site.iplease.accountserver.domain.profile.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.util.ProfileCommandPolicyValidator
import site.iplease.accountserver.domain.profile.util.ProfileConverter
import site.iplease.accountserver.global.common.repository.AccountRepository

@Service
class ProfileCommandServiceImpl(
    private val profileCommandPolicyValidator: ProfileCommandPolicyValidator,
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val profileConverter: ProfileConverter
): ProfileCommandService {
    override fun changePassword(accountId: Long, emailToken: String, newPassword: String): Mono<Unit> =
        profileCommandPolicyValidator.validateChangePassword(accountId, emailToken, newPassword)
            .map { account -> account.copy(encodedPassword = passwordEncoder.encode(newPassword)) }
            .flatMap { account -> accountRepository.save(account) }
            .map{ }

    override fun updateProfile(profileDto: ProfileDto, accountId: Long): Mono<ProfileDto> =
        profileDto.toMono()
            .map { it.copy(accountId = accountId) }
            .flatMap { profile -> profileConverter.toEntity(profile) }
            .flatMap { account -> accountRepository.findById(accountId).map { account to it } }
            .map { it.first.copy(encodedPassword = it.second.encodedPassword) }
            .flatMap { accountRepository.save(it) }
            .flatMap { account -> profileConverter.toDto(account) }
}