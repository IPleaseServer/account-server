package site.iplease.accountserver.domain.register.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import site.iplease.accountserver.global.register.data.type.AccountType
import site.iplease.accountserver.domain.register.data.type.DepartmentType

@Table
data class Account(
    @Id val id: Long = 0,
    val type: AccountType,
    val name: String,
    val email: String,
    val encodedPassword: String,
    val studentNumber: Int = 1101,
    val department: DepartmentType = DepartmentType.SOFTWARE_DEVELOP
)
