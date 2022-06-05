package site.iplease.accountserver.domain.register.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.register.controller.RegisterController
import site.iplease.accountserver.domain.register.exception.WrongDepartmentOrStudentNumberException
import site.iplease.accountserver.domain.register.exception.WrongEmailTokenException

@RestControllerAdvice(basePackageClasses = [RegisterController::class])
class RegisterControllerAdvice {
    @ExceptionHandler(WrongDepartmentOrStudentNumberException::class)
    fun handle(e: WrongDepartmentOrStudentNumberException) =
        ResponseEntity.badRequest().body(e.message).toMono()

    @ExceptionHandler(WrongEmailTokenException::class)
    fun handle(e: WrongEmailTokenException) =
        ResponseEntity.badRequest().body(e.message)
}