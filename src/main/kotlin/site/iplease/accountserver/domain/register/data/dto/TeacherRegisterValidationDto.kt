package site.iplease.accountserver.domain.register.data.dto

data class TeacherRegisterValidationDto(
    val name: String,
    val emailToken: String,
    val password: String,
    val teacherCode: String,
)