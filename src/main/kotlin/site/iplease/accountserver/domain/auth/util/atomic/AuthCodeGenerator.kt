package site.iplease.accountserver.domain.auth.util.atomic

import reactor.core.publisher.Mono

interface AuthCodeGenerator {
    fun generate(): Mono<String> //현존하는 모든 인증코드는 고유해야한다.
}
