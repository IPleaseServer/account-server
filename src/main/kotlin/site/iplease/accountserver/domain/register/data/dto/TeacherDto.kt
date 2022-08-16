package site.iplease.accountserver.domain.register.data.dto

import site.iplease.accountserver.global.common.type.PermissionType

data class TeacherDto(
    val id: Long,
    val permission: PermissionType,
    val name: String,
    val email: String,
    val encodedPassword: String,
    val profileImageUrl: String,
)