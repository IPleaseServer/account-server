package site.iplease.accountserver.global.common.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import site.iplease.accountserver.global.common.type.AccountType
import site.iplease.accountserver.global.common.type.DepartmentType
import site.iplease.accountserver.global.common.type.PermissionType

@Table
data class Account(
    @Id val id: Long = 0,
    val type: AccountType,
    val permission: PermissionType,
    val name: String,
    val email: String,
    val encodedPassword: String,
    val studentNumber: Int = 1101,
    val department: DepartmentType = DepartmentType.SOFTWARE_DEVELOP
)
