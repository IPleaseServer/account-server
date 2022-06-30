package site.iplease.accountserver.domain.register.util.atomic

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder

@Component
class JwtTeacherCodeChecker(
    private val authTokenDecoder: AuthTokenDecoder
): TeacherCodeChecker {
    override fun valid(teacherCode: String): Mono<Unit> =
        authTokenDecoder.decode(AuthTokenDto(token = teacherCode))
            .map {  }
}