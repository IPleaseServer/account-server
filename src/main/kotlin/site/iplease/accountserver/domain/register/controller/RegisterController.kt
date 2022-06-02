package site.iplease.accountserver.domain.register.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.request.StudentRegisterRequest
import site.iplease.accountserver.domain.register.data.request.TeacherRegisterRequest
import site.iplease.accountserver.domain.register.data.response.RegisterResponse
import site.iplease.accountserver.domain.register.util.RegisterPreprocessor
import site.iplease.accountserver.domain.register.service.RegisterService
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/v1/account/register")
class RegisterController(
    private val registerPreprocessor: RegisterPreprocessor,
    private val registerService: RegisterService
) {
    @PostMapping("/student")
    fun registerStudent(@Valid request: StudentRegisterRequest): Mono<ResponseEntity<RegisterResponse>> =
        registerPreprocessor.valid(request)
            .flatMap { registerPreprocessor.decode(request) }
            .flatMap { registerService.registerStudent(it.first, it.second) }
            .map { RegisterResponse(it) }
            .map { ResponseEntity.ok(it) }
    @PostMapping("/teacher")
    fun registerTeacher(@Valid request: TeacherRegisterRequest): Mono<ResponseEntity<RegisterResponse>> =
        registerPreprocessor.valid(request)
            .flatMap { registerPreprocessor.decode(request) }
            .flatMap { registerService.registerTeacher(it.first, it.second) }
            .map { RegisterResponse(it) }
            .map { ResponseEntity.ok(it) }
}