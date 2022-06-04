package site.iplease.accountserver.domain.login.data.response

data class LoginTokenResponse (
    val accessToken: String,
    val refreshToken: String
)