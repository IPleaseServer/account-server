package site.iplease.accountserver.domain.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.auth.data.response.AuthTokenResponse
import site.iplease.accountserver.domain.auth.service.AuthorizeService
import site.iplease.accountserver.domain.auth.validate.AuthCode
import javax.validation.constraints.Email

@Validated
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

    @PostMapping("/email/{email}")
    fun authorizeEmail(@PathVariable @Email email: String): Mono<ResponseEntity<Unit>> =
        authorizeService.authorizeEmail(email)//이메일 인증로직을 수행한다.
            .map { ResponseEntity.ok(it) }//Payload가 비어있는 ResponseEntity(Status=OK)를 반환한다.

    @PostMapping("/teacher/{identifier}")
    fun authorizeTeacher(@PathVariable identifier: String): Mono<ResponseEntity<Unit>> =
        authorizeService.authorizeTeacher(identifier)//교사 인증로직을 수행한다.
            .map { ResponseEntity.ok(it) }//Payload가 비어있는 ResponseEntity(Status=OK)를 반환한다.
}