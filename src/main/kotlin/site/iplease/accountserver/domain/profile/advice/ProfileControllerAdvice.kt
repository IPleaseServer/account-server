package site.iplease.accountserver.domain.profile.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.controller.ProfileController
import site.iplease.accountserver.global.common.exception.UnknownAccountException

@RestControllerAdvice(basePackageClasses = [ProfileController::class])
class ProfileControllerAdvice {
    @ExceptionHandler(UnknownAccountException::class)
    fun handle(e: UnknownAccountException): Mono<ResponseEntity<String>> =
        ResponseEntity.ok(e.message).toMono()
}