package site.iplease.accountserver.global.common.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.global.common.exception.PolicyViolationException
import site.iplease.accountserver.global.error.ErrorResponse
import site.iplease.accountserver.global.error.ErrorStatus

@RestControllerAdvice
class PolicyAdvice {
    @ExceptionHandler(PolicyViolationException::class)
    fun handle(e: PolicyViolationException) =
        ResponseEntity.badRequest().body(
            ErrorResponse(
                status = ErrorStatus.POLICY_VIOLATION_ERROR,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()
}