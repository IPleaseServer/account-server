package site.iplease.accountserver.domain.auth.data.entity

import site.iplease.accountserver.domain.auth.data.type.AuthType

data class AuthCode(
    val code: String,
    val type: AuthType,
    val data: String
)
