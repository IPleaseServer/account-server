package site.iplease.accountserver.domain.register.data.event

import site.iplease.accountserver.global.common.type.PermissionType

data class TeacherRegisteredEvent (
    val id: Long,
    val permission: PermissionType,
    val name: String,
    val email: String,
    val encodedPassword: String,
    val profileImageUrl: String,
)
