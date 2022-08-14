package site.iplease.accountserver.domain.register.data.dto

data class TeacherRegistrationDto (
    val name: String,
    val email: String,
    val encodedPassword: String,
)
