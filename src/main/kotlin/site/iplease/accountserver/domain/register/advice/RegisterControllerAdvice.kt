package site.iplease.accountserver.domain.register.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.register.controller.RegisterController
import site.iplease.accountserver.domain.register.exception.WrongDepartmentOrStudentNumberException
import site.iplease.accountserver.global.auth.exception.WrongEmailTokenException
import site.iplease.accountserver.global.error.ErrorResponse
import site.iplease.accountserver.global.error.ErrorStatus

@RestControllerAdvice(basePackageClasses = [RegisterController::class])
class RegisterControllerAdvice {
    @ExceptionHandler(WrongDepartmentOrStudentNumberException::class)
    fun handle(e: WrongDepartmentOrStudentNumberException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest().body(
            ErrorResponse(
                status = ErrorStatus.WRONG_DEPARTMENT_OR_STUDENT_NUMBER_ERROR,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()

    @ExceptionHandler(WrongEmailTokenException::class)
    fun handle(e: WrongEmailTokenException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest().body(
            ErrorResponse(
                status = ErrorStatus.WRONG_EMAIL_TOKEN_ERROR,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()
}