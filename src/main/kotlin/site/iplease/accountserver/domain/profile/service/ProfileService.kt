package site.iplease.accountserver.domain.profile.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.dto.ProfileDto

interface ProfileService {
    //액세스 토큰을 추출한다.
    //액세스토큰을 검증하여, accountId를 가져온다.
    //accountId를 통해 계정의 프로필을 가져온다.
    //프로필을 반환한다.
    fun getProfileByAccessToken(substring: String): Mono<ProfileDto>
    //accountId를 통해 계정의 프로필을 가져온다.
    //프로필을 반환한다.
    fun getProfileByAccountId(accountId: Long): Mono<ProfileDto>
}
