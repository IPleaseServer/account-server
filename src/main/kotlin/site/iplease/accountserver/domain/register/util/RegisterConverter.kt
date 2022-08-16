package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.*
import site.iplease.accountserver.domain.register.data.event.StudentRegisteredEvent
import site.iplease.accountserver.domain.register.data.event.TeacherRegisteredEvent
import site.iplease.accountserver.domain.register.data.request.StudentRegisterRequest
import site.iplease.accountserver.domain.register.data.request.TeacherRegisterRequest
import site.iplease.accountserver.domain.register.data.response.RegisterResponse
import site.iplease.accountserver.global.common.entity.Account

interface RegisterConverter {
    fun toValidationDto(request: StudentRegisterRequest): Mono<StudentRegisterValidationDto>
    fun toStudentRegisteredEvent(dto: StudentDto): Mono<StudentRegisteredEvent>
    fun toRegisterResponse(dto: StudentDto): Mono<RegisterResponse>
    fun toEntity(dto: StudentRegistrationDto): Mono<Account>
    fun toStudentDto(entity: Account): Mono<StudentDto>
    fun toValidationDto(request: TeacherRegisterRequest): Mono<TeacherRegisterValidationDto>
    fun toTeacherRegisteredEvent(dto: TeacherDto): Mono<TeacherRegisteredEvent>
    fun toRegisterResponse(dto: TeacherDto): Mono<RegisterResponse>
    fun toEntity(dto: TeacherRegistrationDto): Mono<Account>
    fun toTeacherDto(savedEntity: Account): Mono<TeacherDto>
}