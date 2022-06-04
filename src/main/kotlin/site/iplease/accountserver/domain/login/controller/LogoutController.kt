package site.iplease.accountserver.domain.login.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.login.service.LoginService

@Validated
@RestController
@RequestMapping("/api/v1/account/logout")
class LogoutController(
    private val loginService: LoginService
) {
    @DeleteMapping //TODO 나중에 UserDetailService 도입
    fun logout(@RequestHeader authorization: String): Mono<ResponseEntity<Unit>> =
        loginService.logout(authorization.substring("Bearer ".length))
            .map { ResponseEntity.ok(it) }
}