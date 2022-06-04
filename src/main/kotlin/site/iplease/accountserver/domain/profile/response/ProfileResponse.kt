package site.iplease.accountserver.domain.profile.response

import site.iplease.accountserver.global.register.data.type.DepartmentType
import site.iplease.accountserver.global.register.data.type.AccountType

data class ProfileResponse (
    val type: AccountType,
    val common: CommonProfileResponse,
    val student: StudentProfileResponse?,
    val teacher: TeacherProfileResponse?
)

data class CommonProfileResponse (
    val accountId: Long,
    val name: String,
    val email: String,
    val profileImage: String
)

data class StudentProfileResponse (
    val schoolNumber: Int,
    val department: DepartmentType
)

class TeacherProfileResponse()