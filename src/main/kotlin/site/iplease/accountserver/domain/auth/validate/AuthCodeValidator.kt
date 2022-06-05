package site.iplease.accountserver.domain.auth.validate

import site.iplease.accountserver.domain.auth.util.atomic.AuthCodeValidator
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class AuthCodeValidator(private val validator: AuthCodeValidator): ConstraintValidator<AuthCode, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean = validator.valid(value).block()!!
}