package site.iplease.accountserver.domain.register.data.request

import site.iplease.accountserver.global.common.type.DepartmentType

data class StudentRegisterRequest (
    val name: String,
    val emailToken: String,
    val password: String,
    val studentNumber: String,
    val department: DepartmentType
)
