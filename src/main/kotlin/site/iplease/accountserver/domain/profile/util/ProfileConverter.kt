package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.global.common.entity.Account

interface ProfileConverter {
    fun toEntity(profile: ProfileDto): Mono<Account>
    fun toDto(account: Account): Mono<ProfileDto>
}