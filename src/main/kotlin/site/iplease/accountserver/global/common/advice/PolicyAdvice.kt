package site.iplease.accountserver.global.common.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.global.common.exception.PolicyViolationException

@RestControllerAdvice
class PolicyAdvice {
    @ExceptionHandler(PolicyViolationException::class)
    fun handle(e: PolicyViolationException) =
        ResponseEntity.badRequest().body(e.message).toMono()
}