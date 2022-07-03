package site.iplease.accountserver.global.auth.util.atomic

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.auth.data.dto.AuthDto

interface EmailTokenValidator {
    fun validate(dto: AuthDto): Mono<Unit>
}