package site.iplease.accountserver.domain.login.util.atomic

import reactor.core.publisher.Mono

interface RefreshTokenDecoder {
    fun decode(refreshToken: String): Mono<Long>
}
