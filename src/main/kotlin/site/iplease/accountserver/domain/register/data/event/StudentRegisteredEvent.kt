package site.iplease.accountserver.domain.register.data.event

import site.iplease.accountserver.global.common.type.DepartmentType

data class StudentRegisteredEvent(val department: DepartmentType)