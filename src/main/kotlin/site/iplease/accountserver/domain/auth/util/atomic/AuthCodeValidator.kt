package site.iplease.accountserver.domain.auth.util.atomic

import reactor.core.publisher.Mono

interface AuthCodeValidator {
    fun valid(input: String): Mono<Boolean>
}