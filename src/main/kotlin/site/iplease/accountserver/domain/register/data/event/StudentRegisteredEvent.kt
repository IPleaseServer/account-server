package site.iplease.accountserver.domain.register.data.event

import site.iplease.accountserver.global.common.type.DepartmentType
import site.iplease.accountserver.global.common.type.PermissionType

data class StudentRegisteredEvent(
    val id: Long,
    val permission: PermissionType,
    val name: String,
    val email: String,
    val encodedPassword: String,
    val profileImageUrl: String,
    val studentNumber: Int,
    val department: DepartmentType,
)