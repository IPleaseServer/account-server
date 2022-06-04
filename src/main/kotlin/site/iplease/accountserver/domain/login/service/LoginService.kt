package site.iplease.accountserver.domain.login.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.login.data.dto.LoginTokenDto

interface LoginService {
    fun login(email: String, password: String): Mono<LoginTokenDto>
    fun refreshLogin(refreshToken: String): Mono<LoginTokenDto>
    fun logout(accessToken: String): Mono<Unit>
}
