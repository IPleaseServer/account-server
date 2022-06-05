package site.iplease.accountserver.global.login.util.atomic

import reactor.core.publisher.Mono

interface AccessTokenDecoder {
    fun decode(token: String): Mono<Long>
}