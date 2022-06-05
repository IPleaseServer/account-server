package site.iplease.accountserver.global.error

data class ErrorResponse (
    val status: ErrorStatus,
    val message: String,
    val detail: String
)