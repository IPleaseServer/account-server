package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto
import site.iplease.accountserver.domain.register.data.dto.TeacherRegisterValidationDto

interface RegisterValidator {
    fun validate(validationDto: StudentRegisterValidationDto): Mono<StudentRegisterValidationDto>
    fun validate(validationDto: TeacherRegisterValidationDto): Mono<TeacherRegisterValidationDto>
}
