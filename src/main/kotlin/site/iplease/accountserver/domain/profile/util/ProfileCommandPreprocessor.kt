package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.global.common.entity.Account

interface ProfileCommandPreprocessor {
    fun validateChangePassword(accountId: Long, emailToken: String): Mono<Account>
    fun validateUpdateProfile(accountId: Long, request: UpdateProfileRequest): Mono<ProfileDto>
}