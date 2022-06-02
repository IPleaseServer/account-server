package site.iplease.accountserver.domain.register.data.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Pattern

data class TeacherRegisterRequest (
    @field:Length(min = 2, max = 5)
    val name: String,
    @field:Pattern(regexp = "^([^.]+\\.){2}[^.]*\$")
    val emailToken: String,
    val password: String,
    val teacherCode: String
)