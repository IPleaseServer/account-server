package site.iplease.accountserver.domain.auth.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.domain.auth.data.type.AuthType

interface AuthTokenEncoder {
    fun encodeAuthToken(type: AuthType, data: String): Mono<AuthTokenDto>
}
