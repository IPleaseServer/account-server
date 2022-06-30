package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest

interface ProfileConverter {
    fun toDto(request: UpdateProfileRequest): Mono<ProfileDto>
}
