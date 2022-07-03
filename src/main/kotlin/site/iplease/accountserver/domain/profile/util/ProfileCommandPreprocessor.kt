package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.domain.profile.data.response.ChangePasswordRequest

interface ProfileCommandPreprocessor {
    fun validate(accountId: Long, request: ChangePasswordRequest): Mono<Unit>
    fun validate(accountId: Long, request: UpdateProfileRequest): Mono<Unit>
    fun convert(accountId: Long, request: UpdateProfileRequest): Mono<ProfileDto>
}