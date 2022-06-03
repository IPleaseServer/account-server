package site.iplease.accountserver.domain.login.util.atomic

import reactor.core.publisher.Mono

interface RefreshTokenRemover {
    fun remove(token: String): Mono<String>
}
