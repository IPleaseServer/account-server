package site.iplease.accountserver.domain.register.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.register.data.dto.*
import site.iplease.accountserver.domain.register.data.event.StudentRegisteredEvent
import site.iplease.accountserver.domain.register.data.event.TeacherRegisteredEvent
import site.iplease.accountserver.domain.register.data.request.StudentRegisterRequest
import site.iplease.accountserver.domain.register.data.request.TeacherRegisterRequest
import site.iplease.accountserver.domain.register.data.response.RegisterResponse
import site.iplease.accountserver.global.common.entity.Account
import site.iplease.accountserver.global.common.type.AccountType
import site.iplease.accountserver.global.common.type.PermissionType

@Component
class RegisterConverterImpl: RegisterConverter {
    override fun toValidationDto(request: StudentRegisterRequest): Mono<StudentRegisterValidationDto> =
        StudentRegisterValidationDto(
            name = request.name,
            emailToken = request.emailToken,
            password = request.password,
            studentNumber = request.studentNumber,
            department = request.department,
        ).toMono()

    override fun toValidationDto(request: TeacherRegisterRequest): Mono<TeacherRegisterValidationDto> =
        TeacherRegisterValidationDto(
            name = request.name,
            emailToken = request.emailToken,
            password = request.password,
            teacherCode = request.teacherCode
        ).toMono()

    override fun toStudentRegisteredEvent(dto: StudentDto): Mono<StudentRegisteredEvent> =
        StudentRegisteredEvent(
            id = dto.id,
            permission = dto.permission,
            name = dto.name,
            email = dto.email,
            encodedPassword = dto.encodedPassword,
            profileImageUrl = dto.profileImageUrl,
            studentNumber = dto.studentNumber,
            department = dto.department,
        ).toMono()

    override fun toRegisterResponse(dto: StudentDto): Mono<RegisterResponse> =
        RegisterResponse(accountId = dto.id).toMono()

    override fun toRegisterResponse(dto: TeacherDto): Mono<RegisterResponse> =
        RegisterResponse(accountId = dto.id).toMono()

    override fun toEntity(dto: StudentRegistrationDto): Mono<Account> =
        Account(
            id = 0,
            type = AccountType.STUDENT,
            permission = PermissionType.USER,
            name = dto.name,
            email = dto.email,
            encodedPassword = dto.encodedPassword,
            profileImageUrl = dto.profileImageUrl,
            studentNumber = dto.studentNumber,
            department = dto.department,
        ).toMono()

    override fun toEntity(dto: TeacherRegistrationDto): Mono<Account> =
        Account(
            id = 0,
            type = AccountType.TEACHER,
            permission = PermissionType.OPERATOR,
            name = dto.name,
            email = dto.email,
            encodedPassword = dto.encodedPassword,
            profileImageUrl = dto.profileImageUrl,
        ).toMono()

    override fun toStudentDto(entity: Account): Mono<StudentDto> =
        StudentDto(
            id = entity.id,
            permission = entity.permission,
            name = entity.name,
            email = entity.email,
            encodedPassword = entity.encodedPassword,
            profileImageUrl = entity.profileImageUrl,
            studentNumber = entity.studentNumber,
            department = entity.department,
        ).toMono()

    override fun toTeacherRegisteredEvent(dto: TeacherDto): Mono<TeacherRegisteredEvent> =
        TeacherRegisteredEvent(
            id = dto.id,
            permission = dto.permission,
            name = dto.name,
            email = dto.email,
            encodedPassword = dto.encodedPassword,
            profileImageUrl = dto.profileImageUrl,
        ).toMono()

    override fun toTeacherDto(savedEntity: Account): Mono<TeacherDto> =
        TeacherDto(
            id = savedEntity.id,
            permission = savedEntity.permission,
            name = savedEntity.name,
            email = savedEntity.email,
            encodedPassword = savedEntity.encodedPassword,
            profileImageUrl = savedEntity.profileImageUrl,
        ).toMono()
}