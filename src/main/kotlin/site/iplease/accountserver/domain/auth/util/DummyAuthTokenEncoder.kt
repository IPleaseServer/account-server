package site.iplease.accountserver.domain.auth.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.domain.auth.data.type.AuthType

@Component //TODO 실제 구현체 작성 후 제거 예정
class DummyAuthTokenEncoder: AuthTokenEncoder {
    override fun encodeAuthToken(type: AuthType, data: String): Mono<AuthTokenDto> =
        AuthTokenDto("authToken-$type-$data").toMono()
}