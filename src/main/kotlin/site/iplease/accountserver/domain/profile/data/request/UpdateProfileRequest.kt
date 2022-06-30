package site.iplease.accountserver.domain.profile.data.request

import site.iplease.accountserver.global.common.type.AccountType
import site.iplease.accountserver.global.common.type.DepartmentType
import site.iplease.accountserver.global.common.type.PermissionType
import java.net.URI

data class UpdateProfileRequest (
    val type: AccountType,
    val permission: PermissionType,
    val name: String?,
    val emailToken: String, //사용자 인증을 위한 이메일 토큰
    val newEmailToken: String?, //새로운 이메일로의 변경을 위한 이메일 토큰
    val profileImage: URI?,
    val studentNumber: Int?,
    val department: DepartmentType?
)
