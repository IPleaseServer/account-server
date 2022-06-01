package site.iplease.accountserver.domain.auth.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component //TODO 실제 구현체 작성 후 제거 예정
class DummyAuthCodeGenerator(): AuthCodeGenerator {
    override fun generate(): Mono<String> = "인증코드입니다".toMono()
}