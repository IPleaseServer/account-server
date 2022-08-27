package site.iplease.accountserver.domain.profile.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto

interface ProfileService {
    fun getProfileByAccessToken(accessToken: String): Mono<ProfileDto>
    fun getProfileByAccountId(accountId: Long): Mono<ProfileDto>
    fun existProfileByAccessToken(accessToken: String): Mono<Boolean>
    fun existsProfileByEmail(email: String): Mono<Boolean>
    fun getProfileByEmailToken(emailToken: String): Mono<ProfileDto>
}
