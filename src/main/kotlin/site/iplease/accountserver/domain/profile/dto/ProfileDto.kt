package site.iplease.accountserver.domain.profile.dto

import site.iplease.accountserver.global.register.data.type.AccountType
import site.iplease.accountserver.global.register.data.type.DepartmentType
import java.net.URI

data class ProfileDto (
    val type: AccountType,
    val accountId: Long,
    val name: String,
    val email: String,
    val profileImage: URI,
    val studentNumber: Int,
    val department: DepartmentType
)