package site.iplease.accountserver.domain.profile.response

data class ChangePasswordRequest (
    val newPassword: String,
    val emailToken: String
)
