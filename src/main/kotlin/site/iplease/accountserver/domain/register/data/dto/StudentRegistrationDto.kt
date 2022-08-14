package site.iplease.accountserver.domain.register.data.dto

import site.iplease.accountserver.global.common.type.DepartmentType

data class StudentRegistrationDto(
    val name: String,
    val email: String,
    val encodedPassword: String,
    val studentNumber: Int,
    val department: DepartmentType,
    val profileImageUrl: String
)
