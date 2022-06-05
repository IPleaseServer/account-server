package site.iplease.accountserver.domain.login.data.entity

data class RefreshToken (
    val token: String,
    val accountId: Long
)