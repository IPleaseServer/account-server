package site.iplease.accountserver.domain.auth.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.auth.data.dto.AuthDto
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto

interface AuthTokenDecoder {
    fun decode(dto: AuthTokenDto): Mono<AuthDto>
}