package site.iplease.accountserver.domain.register.policy

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto

interface StudentRegistrationPolicy {
    fun validate(validationDto: StudentRegisterValidationDto): Mono<Unit>
}
