package site.iplease.accountserver.domain.register.data.dto

import site.iplease.accountserver.global.register.data.type.DepartmentType

data class StudentAdditionalRegisterDto (val studentNumber: Int, val department: DepartmentType)