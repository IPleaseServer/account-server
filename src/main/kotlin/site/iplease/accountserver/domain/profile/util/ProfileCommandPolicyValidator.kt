package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.global.common.entity.Account

interface ProfileCommandPolicyValidator {
    fun validateChangePassword(accountId: Long, emailToken: String, newPassword: String): Mono<Account>
    fun validateUpdateProfile(accountId: Long, request: UpdateProfileRequest): Mono<ProfileDto>
}