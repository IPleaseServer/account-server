package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidatedDto
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto

interface RegisterValidator {
    fun validate(validationDto: StudentRegisterValidationDto): Mono<StudentRegisterValidatedDto>

}
