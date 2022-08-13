package site.iplease.accountserver.domain.register.data.dto

import site.iplease.accountserver.global.common.type.DepartmentType

data class StudentRegisterValidatedDto(
    val department: DepartmentType
)