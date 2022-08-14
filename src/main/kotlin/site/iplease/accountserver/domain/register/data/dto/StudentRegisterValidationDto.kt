package site.iplease.accountserver.domain.register.data.dto

import site.iplease.accountserver.global.common.type.DepartmentType

data class StudentRegisterValidationDto(
    val name: String,
    val emailToken: String,
    val password: String,
    val studentNumber: Int,
    val department: DepartmentType
)
