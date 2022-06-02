package site.iplease.accountserver.domain.register.data.dto

data class CommonRegisterDto (
    val name: String,
    val emailToken: String,
    val password: String
)
