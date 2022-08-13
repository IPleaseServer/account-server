package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto
import site.iplease.accountserver.domain.register.data.event.StudentRegisteredEvent
import site.iplease.accountserver.domain.register.data.request.StudentRegisterRequest
import site.iplease.accountserver.domain.register.data.response.RegisterResponse
import site.iplease.accountserver.domain.register.dto.StudentRegistrationDto

interface RegisterConverter {
    fun toValidationDto(request: StudentRegisterRequest): Mono<StudentRegisterValidationDto>
    fun toDto(validationDto: StudentRegisterValidationDto): Mono<StudentRegistrationDto>
    fun toStudentRegisteredEvent(dto: StudentRegistrationDto): Mono<StudentRegisteredEvent>
    fun toRegisterResponse(dto: StudentRegistrationDto): Mono<RegisterResponse>

}
