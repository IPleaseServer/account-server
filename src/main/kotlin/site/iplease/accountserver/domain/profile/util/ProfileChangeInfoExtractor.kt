package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.global.common.entity.Account

interface ProfileChangeInfoExtractor {
    fun extract(accountId: Long, request: UpdateProfileRequest, email: String): Mono<Pair<Account, String>>
}
