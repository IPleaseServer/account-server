package site.iplease.accountserver.domain.auth.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.auth.controller.AuthorizeController
import site.iplease.accountserver.domain.auth.exception.WrongAuthCodeException
import site.iplease.accountserver.global.error.ErrorResponse
import site.iplease.accountserver.global.error.ErrorStatus
import site.iplease.accountserver.infra.teacher_code.exception.TeacherAuthorizerDuplicatedKeyException
import javax.validation.ConstraintViolationException

@RestControllerAdvice(basePackageClasses = [AuthorizeController::class, AuthorizeControllerAdvice::class])
class AuthorizeControllerAdvice {
    @ExceptionHandler(ConstraintViolationException::class)
    fun handle(e: ConstraintViolationException): Mono<ResponseEntity<ErrorResponse>> = ResponseEntity
        .badRequest()
        .body(ErrorResponse(
            status = ErrorStatus.CONSTRAINT_VIOLATION_ERROR,
            message = "요청정보가 정책을 위반하였습니다.",
            detail = e.localizedMessage
        )).toMono()

    @ExceptionHandler(TeacherAuthorizerDuplicatedKeyException::class)
    fun handle(e: TeacherAuthorizerDuplicatedKeyException): Mono<ResponseEntity<ErrorResponse>> = ResponseEntity
        .badRequest()
        .body(ErrorResponse(
            status = ErrorStatus.POLICY_VIOLATION_ERROR,
            message = e.getErrorMessage(),
            detail = e.getErrorDetail()
        )).toMono()

    @ExceptionHandler(WrongAuthCodeException::class)
    fun handle(e: WrongAuthCodeException): Mono<ResponseEntity<ErrorResponse>> = ResponseEntity.badRequest()
        .body(ErrorResponse(
            status = ErrorStatus.WRONG_AUTH_CODE,
            message = e.getErrorMessage(),
            detail = e.getErrorDetail()
        )).toMono()
}