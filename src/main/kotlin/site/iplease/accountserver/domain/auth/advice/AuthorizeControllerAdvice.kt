package site.iplease.accountserver.domain.auth.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class AuthorizeControllerAdvice {
    //TODO 나중에 ErrorResponse 반환하도록 수정하기
    @ExceptionHandler(ConstraintViolationException::class)
    fun handle(e: ConstraintViolationException): Mono<ResponseEntity<String>> = ResponseEntity.ok(e.localizedMessage).toMono()
}