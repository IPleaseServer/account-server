package site.iplease.accountserver.domain.register.util.atomic

import reactor.core.publisher.Mono

interface RegisterEmailValidator {
    fun valid(email: String): Mono<Unit>
}
