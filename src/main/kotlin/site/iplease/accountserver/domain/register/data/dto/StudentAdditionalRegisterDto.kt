package site.iplease.accountserver.domain.register.data.dto

import site.iplease.accountserver.domain.register.data.type.DepartmentType

data class StudentAdditionalRegisterDto (val studentNumber: Int, val department: DepartmentType)