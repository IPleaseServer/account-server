package site.iplease.accountserver.domain.login.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.entity.Account

interface RefreshTokenGenerator {
    fun generate(account: Account): Mono<String>
}
