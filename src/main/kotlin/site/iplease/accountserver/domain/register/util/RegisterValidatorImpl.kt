package site.iplease.accountserver.domain.register.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto
import site.iplease.accountserver.domain.register.policy.StudentRegistrationPolicy
import site.iplease.accountserver.domain.register.rule.StudentRegistrationRule

@Component
class RegisterValidatorImpl(
    private val studentRegistrationRule: StudentRegistrationRule,
    private val studentRegistrationPolicy: StudentRegistrationPolicy
): RegisterValidator {
    override fun validate(validationDto: StudentRegisterValidationDto): Mono<StudentRegisterValidationDto> =
        studentRegistrationRule.validate(validationDto)
            .flatMap { studentRegistrationPolicy.validate(validationDto) }
            .map { validationDto }
}