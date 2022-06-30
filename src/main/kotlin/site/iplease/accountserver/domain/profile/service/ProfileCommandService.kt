package site.iplease.accountserver.domain.profile.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto

interface ProfileCommandService {
    fun changePassword(accountId: Long, emailToken: String, newPassword: String): Mono<Unit>
    fun updateProfile(profileDto: ProfileDto, accountId: Long): Mono<ProfileDto>

}
