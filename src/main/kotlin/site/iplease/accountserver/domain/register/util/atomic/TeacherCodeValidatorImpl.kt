package site.iplease.accountserver.domain.register.util.atomic

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder

@Component
class TeacherCodeValidatorImpl(
    private val authTokenDecoder: AuthTokenDecoder
): TeacherCodeValidator {
    override fun valid(teacherCode: String): Mono<Unit> =
        authTokenDecoder.decode(AuthTokenDto(token = teacherCode))
            .map {  }
}