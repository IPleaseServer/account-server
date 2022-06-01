package site.iplease.accountserver.domain.auth.util.atomic

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.auth.data.dto.AuthDto
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto

interface AuthTokenEncoder {
    fun encode(dto: AuthDto): Mono<AuthTokenDto>
}
