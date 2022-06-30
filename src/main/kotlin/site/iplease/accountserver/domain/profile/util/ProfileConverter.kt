package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.global.common.entity.Account

interface ProfileConverter {
    fun toEntity(profile: ProfileDto): Mono<Account>
    fun toDto(account: Account): Mono<ProfileDto>
    fun toDto(account: UpdateProfileRequest): Mono<ProfileDto>
}