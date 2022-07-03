package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.global.common.entity.Account

interface ProfileChangeInfoConverter {
    fun convert(accountId: Long, request: UpdateProfileRequest): Mono<Pair<Account, String>>
}
