package site.iplease.accountserver.domain.register.data.request

import org.hibernate.validator.constraints.Length
import site.iplease.accountserver.global.register.data.type.DepartmentType
import javax.validation.constraints.Pattern

data class StudentRegisterRequest (
    @field:Length(min = 2, max = 5)
    val name: String,
    val emailToken: String,
    val password: String,
    @field:Pattern(regexp = "[0-3][1-4][0-2][0-9]")
    val studentNumber: String,
    val department: DepartmentType
)
