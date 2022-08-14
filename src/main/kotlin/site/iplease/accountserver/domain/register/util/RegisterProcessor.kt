package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto
import site.iplease.accountserver.domain.register.data.dto.StudentRegistrationDto
import site.iplease.accountserver.domain.register.data.dto.TeacherRegisterValidationDto
import site.iplease.accountserver.domain.register.data.dto.TeacherRegistrationDto

interface RegisterProcessor {
    fun process(validatedDto: StudentRegisterValidationDto): Mono<StudentRegistrationDto>
    fun process(validatedDto: TeacherRegisterValidationDto): Mono<TeacherRegistrationDto>
}
