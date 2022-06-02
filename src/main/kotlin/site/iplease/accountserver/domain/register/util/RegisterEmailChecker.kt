package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono

interface RegisterEmailChecker {
    fun valid(email: String): Mono<Unit>
}
