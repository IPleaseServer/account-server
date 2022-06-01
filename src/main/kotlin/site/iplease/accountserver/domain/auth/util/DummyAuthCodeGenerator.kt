package site.iplease.accountserver.domain.auth.util

import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.*

class DummyAuthCodeGenerator(): AuthCodeGenerator {
    //현존하는 모든 인증코드는 고유해야한다.
    override fun generate(): Mono<String> = UUID.randomUUID().toString().toMono()
}