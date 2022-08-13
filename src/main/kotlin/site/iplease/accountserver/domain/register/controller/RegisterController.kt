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
import site.iplease.accountserver.domain.register.util.LegacyRegisterPreprocessor
import site.iplease.accountserver.domain.register.service.LegacyRegisterService
import site.iplease.accountserver.domain.register.service.RegisterService
import site.iplease.accountserver.domain.register.util.RegisterConverter
import site.iplease.accountserver.domain.register.util.RegisterValidator
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/v1/account/register")
class RegisterController(
    private val legacyRegisterPreprocessor: LegacyRegisterPreprocessor,
    private val legacyService: LegacyRegisterService,
    private val registerService: RegisterService,
    private val registerConverter: RegisterConverter,
    private val registerValidator: RegisterValidator,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    //학생 회원가입
    /*메인 비즈니스 로직
    (1) 인자로 받은 요청정보를 검증한다. 검증은 Rule Validation과 Policy Validation으로 나뉜다.
    (2) 검증된 요청정보를 통해서 학생 회원가입 트랜잭션을 수행한다.
    (3) 트랜잭션 수행결과를 통해서 응답값을 구성한다.
    (4) 구성한 응답값을 반환한다.
     */
    /*
    발행하는 이벤트
    (3)의 작업이 끝난 이후 StudentRegisteredEvent를 발행한다.
     */
    @PostMapping("/student")
    fun registerStudent(@RequestBody @Valid request: StudentRegisterRequest): Mono<ResponseEntity<RegisterResponse>> =
        registerConverter.toValidationDto(request)
            .flatMap{ validationDto -> registerValidator.validate(validationDto) } //(1)요청정보를 검증한다.
            .flatMap { validationDto -> registerConverter.toDto(validationDto) } //StudentRegisterDto를 구성한다.
            .flatMap { dto -> registerService.registerStudent(dto) } //(2)학생 회원가입 트랜잭션을 수행한다.
            .flatMap { dto ->
                //이벤트 발행
                registerConverter.toStudentRegisteredEvent(dto) //StudentRegisteredEvent를 구성한다.
                    .map { event -> applicationEventPublisher.publishEvent(event) } //StudentRegisteredEvent를 발행한다.
                //응답값 반환
                    .flatMap { registerConverter.toRegisterResponse(dto) } //(3) 응답값을 구성한다.
                    .map { response -> ResponseEntity.ok(response) } //(4) 구성한 응답값을 반환한다.
            }

    @PostMapping("/teacher")
    fun registerTeacher(@RequestBody @Valid request: TeacherRegisterRequest): Mono<ResponseEntity<RegisterResponse>> =
        legacyRegisterPreprocessor.valid(request)
            .flatMap { legacyRegisterPreprocessor.decodeAndConvert(request) }
            .flatMap { legacyService.registerTeacher(it.first, it.second) }
            .map { RegisterResponse(it) }
            .map { ResponseEntity.ok(it) }
}