package site.iplease.accountserver.domain.register.controller

import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.request.StudentRegisterRequest
import site.iplease.accountserver.domain.register.data.request.TeacherRegisterRequest
import site.iplease.accountserver.domain.register.data.response.RegisterResponse
import site.iplease.accountserver.domain.register.service.RegisterService
import site.iplease.accountserver.domain.register.util.RegisterConverter
import site.iplease.accountserver.domain.register.util.RegisterProcessor
import site.iplease.accountserver.domain.register.util.RegisterValidator
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/v1/account/register")
class RegisterController(
    private val registerService: RegisterService,
    private val registerConverter: RegisterConverter,
    private val registerProcessor: RegisterProcessor,
    private val registerValidator: RegisterValidator,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    //학생 회원가입
    /*메인 비즈니스 로직
    (1) 인자로 받은 요청정보를 검증한다. 검증은 Rule Validation과 Policy Validation으로 나뉜다.
    (2) 요청정보에서 회원가입시 필요한 정보를 추출한다.
    (3) 검증된 요청정보를 통해서 학생 회원가입 트랜잭션을 수행한다.
    (4) 트랜잭션 수행결과를 통해서 응답값을 구성한다.
    (5) 구성한 응답값을 반환한다.
     */
    /*
    발행하는 이벤트
    (3)의 작업이 끝난 이후 StudentRegisteredEvent를 발행한다.
     */
    @PostMapping("/student")
    fun registerStudent(@RequestBody request: StudentRegisterRequest): Mono<ResponseEntity<RegisterResponse>> =
        registerConverter.toValidationDto(request) //StudentRegisterValidationDto를 구성한다.
            .flatMap{ validationDto -> registerValidator.validate(validationDto) } //(1)요청정보를 검증한다.
            .flatMap { validationDto -> registerProcessor.process(validationDto) } //(2)회원가입시 필요한 정보를 추출한다.
            .flatMap { dto -> registerService.registerStudent(dto) } //(3)학생 회원가입 트랜잭션을 수행한다.
            .flatMap { dto ->
                //이벤트 발행
                registerConverter.toStudentRegisteredEvent(dto) //StudentRegisteredEvent를 구성한다.
                    .map { event -> applicationEventPublisher.publishEvent(event) } //StudentRegisteredEvent를 발행한다.
                //응답값 반환
                    .flatMap { registerConverter.toRegisterResponse(dto) } //(4) 응답값을 구성한다.
                    .map { response -> ResponseEntity.ok(response) } //(5) 구성한 응답값을 반환한다.
            }

    @PostMapping("/teacher")
    fun registerTeacher(@RequestBody @Valid request: TeacherRegisterRequest): Mono<ResponseEntity<RegisterResponse>> =
        registerConverter.toValidationDto(request) //TeacherRegisterValidationDto를 구성한다.
            .flatMap{ validationDto -> registerValidator.validate(validationDto) } //(1)요청정보를 검증한다.
            .flatMap { validationDto -> registerProcessor.process(validationDto) } //(2)회원가입시 필요한 정보를 추출한다.
            .flatMap { dto -> registerService.registerTeacher(dto) } //(3)강사 회원가입 트랜잭션을 수행한다.
            .flatMap { dto ->
                //이벤트 발행
                registerConverter.toTeacherRegisteredEvent(dto) //TeacherRegisteredEvent를 구성한다.
                    .map { event -> applicationEventPublisher.publishEvent(event) } //TeacherRegisteredEvent를 발행한다.
                //응답값 반환
                    .flatMap { registerConverter.toRegisterResponse(dto) } //(4) 응답값을 구성한다.
                    .map { response -> ResponseEntity.ok(response) } //(5) 구성한 응답값을 반환한다.
            }
}