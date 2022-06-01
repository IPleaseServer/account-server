package site.iplease.accountserver.domain.auth.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.*

@Component //TODO 실제 구현체 작성 후 제거 예정
class DummyAuthCodeGenerator(): AuthCodeGenerator {
    //현존하는 모든 인증코드는 고유해야한다.
    override fun generate(): Mono<String> = UUID.randomUUID().toString().toMono()
}