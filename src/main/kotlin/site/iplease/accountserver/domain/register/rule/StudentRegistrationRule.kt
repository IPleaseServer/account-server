package site.iplease.accountserver.domain.register.rule

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto

interface StudentRegistrationRule {
    fun validate(validationDto: StudentRegisterValidationDto): Mono<Unit>
}
