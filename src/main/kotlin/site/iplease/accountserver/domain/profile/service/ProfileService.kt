package site.iplease.accountserver.domain.profile.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.dto.ProfileDto

interface ProfileService {
    fun getProfileByAccessToken(accessToken: String): Mono<ProfileDto>
    fun getProfileByAccountId(accountId: Long): Mono<ProfileDto>
    fun existProfileByAccessToken(it: String): Mono<ProfileDto>
}
