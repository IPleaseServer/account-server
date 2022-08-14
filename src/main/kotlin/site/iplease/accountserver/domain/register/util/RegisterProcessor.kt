package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto
import site.iplease.accountserver.domain.register.data.dto.StudentRegistrationDto

interface RegisterProcessor {
    fun process(validatedDto: StudentRegisterValidationDto): Mono<StudentRegistrationDto>
}
