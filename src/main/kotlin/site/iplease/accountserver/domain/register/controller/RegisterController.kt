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
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/v1/account/register")
class RegisterController {
    @PostMapping("/student")
    fun registerStudent(@Valid request: StudentRegisterRequest): Mono<ResponseEntity<RegisterResponse>> {
        //요청정보를 검사하고 해독한다.
        //해독한 요청정보를 토대로 학생 회원가입 정책을 검사한다.
        //회원정보를 구성한다.
        //구성한 회원정보를 저장한다.
        TODO()
    }
    @PostMapping("/teacher")
    fun registerTeacher(@Valid request: TeacherRegisterRequest): Mono<ResponseEntity<RegisterResponse>> {
        //요청정보를 검사하고 해독한다.
        //해독한 요청정보를 토대로 교사 회원가입 정책을 검사한다.
        //회원정보를 구성한다.
        //구성한 회원정보를 저장한다.
        TODO()
    }
}