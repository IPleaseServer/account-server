package site.iplease.accountserver.domain.login.util.atomic

import reactor.core.publisher.Mono
import site.iplease.accountserver.global.common.entity.Account

interface AccessTokenEncoder {
    fun generate(account: Account): Mono<String>
}
