package site.iplease.accountserver.domain.profile.data.response

data class ChangePasswordRequest (
    val newPassword: String,
    val emailToken: String
)
