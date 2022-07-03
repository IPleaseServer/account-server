package site.iplease.accountserver.global.auth.util.atomic

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.auth.data.dto.AuthDto
import site.iplease.accountserver.global.auth.data.type.AuthType
import site.iplease.accountserver.global.auth.exception.WrongEmailTokenException

@Component
class EmailTokenValidatorImpl: EmailTokenValidator {
    override fun validate(dto: AuthDto): Mono<Unit> =
        if (dto.type == AuthType.EMAIL) Unit.toMono()
        else Mono.error(WrongEmailTokenException("해당 토큰은 ${dto.type}토큰입니다!"))
}