package site.iplease.accountserver.domain.auth.data.dto

import site.iplease.accountserver.global.auth.data.type.AuthType

data class AuthDto (val type: AuthType, val data: String)
