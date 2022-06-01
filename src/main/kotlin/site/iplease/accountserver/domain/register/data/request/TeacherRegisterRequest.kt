package site.iplease.accountserver.domain.register.data.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Pattern

data class TeacherRegisterRequest (
    @Length(min = 2, max = 5)
    val name: String,
    @Pattern(regexp = "^([^.]+\\.){2}[^.]*\$")
    val emailToken: String,
    val password: String,
    val teacherCode: String
)