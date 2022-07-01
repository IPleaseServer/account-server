package site.iplease.accountserver.domain.profile.endpoint.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.endpoint.controller.ProfileQueryController
import site.iplease.accountserver.global.common.exception.UnknownAccountException
import site.iplease.accountserver.global.error.ErrorResponse
import site.iplease.accountserver.global.error.ErrorStatus

@RestControllerAdvice(basePackageClasses = [ProfileQueryController::class])
class ProfileQueryControllerAdvice {
    @ExceptionHandler(UnknownAccountException::class)
    fun handle(e: UnknownAccountException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.ok(
            ErrorResponse(
                status = ErrorStatus.UNKNOWN_ACCOUNT_ERROR,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()
}