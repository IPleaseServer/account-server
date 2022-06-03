package site.iplease.accountserver.domain.login.util.atomic

import reactor.core.publisher.Mono

interface LoginDataDecoder {
    fun decode(email: String, password: String): Mono<Long>
}
