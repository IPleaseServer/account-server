package site.iplease.accountserver.global.error

interface IpleaseError {
    fun getErrorMessage(): String
    fun getErrorDetail(): String
}