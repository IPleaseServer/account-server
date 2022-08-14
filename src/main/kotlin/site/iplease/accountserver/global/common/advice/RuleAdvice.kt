package site.iplease.accountserver.global.common.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.global.common.exception.RuleViolationException
import site.iplease.accountserver.global.error.ErrorResponse
import site.iplease.accountserver.global.error.ErrorStatus

@RestControllerAdvice
class RuleAdvice {
    @ExceptionHandler(RuleViolationException::class)
    fun handle(e: RuleViolationException) =
        ResponseEntity.badRequest().body(
            ErrorResponse(
                status = ErrorStatus.RULE_VIOLATION_ERROR,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )
        ).toMono()
}