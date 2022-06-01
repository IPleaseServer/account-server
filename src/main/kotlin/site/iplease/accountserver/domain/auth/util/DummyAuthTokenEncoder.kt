package site.iplease.accountserver.domain.auth.util

import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.domain.auth.data.type.AuthType

class DummyAuthTokenEncoder: AuthTokenEncoder {
    override fun encodeAuthToken(type: AuthType, data: String): Mono<AuthTokenDto> =
        AuthTokenDto("authToken-$type-$data").toMono()
}