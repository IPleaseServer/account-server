package site.iplease.accountserver.domain.auth.util

import reactor.core.publisher.Mono

interface AuthCodeGenerator {
    fun generate(): Mono<String>

}
