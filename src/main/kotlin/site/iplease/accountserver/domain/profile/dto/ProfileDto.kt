package site.iplease.accountserver.domain.profile.dto

import site.iplease.accountserver.global.common.type.AccountType
import site.iplease.accountserver.global.common.type.DepartmentType
import site.iplease.accountserver.global.common.type.PermissionType
import java.net.URI

data class ProfileDto (
    val type: AccountType,
    val permission: PermissionType,
    val accountId: Long,
    val name: String,
    val email: String,
    val profileImage: URI,
    val studentNumber: Int,
    val department: DepartmentType
)