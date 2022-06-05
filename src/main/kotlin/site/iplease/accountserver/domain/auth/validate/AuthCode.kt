package site.iplease.accountserver.domain.auth.validate

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [AuthCodeValidator::class])
annotation class AuthCode(
    val message: String = "authCode",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)