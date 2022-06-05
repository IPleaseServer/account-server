package site.iplease.accountserver.domain.login.data.dto

data class LoginTokenDto (
    val accessToken: String,
    val refreshToken: String
)
