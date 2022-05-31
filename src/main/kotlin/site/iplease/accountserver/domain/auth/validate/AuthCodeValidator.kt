package site.iplease.accountserver.domain.auth.validate

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class AuthCodeValidator: ConstraintValidator<AuthCode, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        TODO("Not yet implemented")
    }
}
