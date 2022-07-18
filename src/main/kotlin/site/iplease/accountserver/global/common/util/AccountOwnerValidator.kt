package site.iplease.accountserver.global.common.util

import reactor.core.publisher.Mono

interface AccountOwnerValidator {
    fun validate(accountId: Long, email: String): Mono<Unit>
}
