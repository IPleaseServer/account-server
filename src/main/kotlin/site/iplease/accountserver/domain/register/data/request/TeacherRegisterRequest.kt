package site.iplease.accountserver.domain.register.data.request

data class TeacherRegisterRequest (
    val name: String,
    val emailToken: String,
    val password: String,
    val teacherCode: String
)