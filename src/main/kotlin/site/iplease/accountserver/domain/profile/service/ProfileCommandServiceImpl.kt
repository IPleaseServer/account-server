package site.iplease.accountserver.domain.profile.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.util.ProfileCommandPolicyValidator
import site.iplease.accountserver.global.common.entity.Account
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

    override fun updateProfile(profileDto: ProfileDto, accountId: Long): Mono<ProfileDto> =
        profileDto.toMono()
            .map { it.copy(accountId = accountId) }
            .map { profile -> Account( //TODO ProfileConverter 쓰기
                id = profile.accountId,
                type = profile.type,
                permission = profile.permission,
                name = profile.name,
                email = profile.email,
                encodedPassword = "",
                studentNumber = profile.studentNumber,
                department = profile.department
            ) }.flatMap { account -> accountRepository.findById(accountId).map { account to it } }
            .map { it.first.copy(encodedPassword = it.second.encodedPassword) }
            .flatMap { accountRepository.save(it) }
            .map { account -> ProfileDto( //TODO ProfileConverter 쓰기
                type = account.type,
                permission = account.permission,
                accountId = account.id,
                name = account.name,
                email = account.email,
                profileImage = profileDto.profileImage, //TODO Account 엔티티에 이미지정보 추가하고 수정
                studentNumber = account.studentNumber,
                department = account.department
            ) }
}