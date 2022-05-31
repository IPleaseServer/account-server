package site.iplease.accountserver.domain.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.auth.response.AuthTokenResponse
import site.iplease.accountserver.domain.auth.service.AuthorizeService
import site.iplease.accountserver.domain.auth.validate.AuthCode
import javax.validation.constraints.Email

@RestController
@RequestMapping("/api/v1/account/auth")
class AuthorizeController(
    private val authorizeService: AuthorizeService
) {
    @GetMapping("/{authCode}")
    fun authorize(@PathVariable @AuthCode authCode: String): Mono<ResponseEntity<AuthTokenResponse>> =
        authorizeService.authorize(authCode)
            .map { AuthTokenResponse(it.token) }
            .map { ResponseEntity.ok(it) }

    @PostMapping("/email")
    fun authorizeEmail(@RequestParam @Email email: String): Mono<ResponseEntity<Unit>> =
        authorizeService.authorizeEmail(email)
            .map { ResponseEntity.ok(it) }

}