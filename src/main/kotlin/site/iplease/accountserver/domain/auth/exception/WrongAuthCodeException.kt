package site.iplease.accountserver.domain.auth.exception

import site.iplease.accountserver.global.error.IpleaseError

class WrongAuthCodeException(message: String, code: String):
    RuntimeException("$ERROR_MESSAGE - $message - $code"), IpleaseError {
    companion object { private const val ERROR_MESSAGE = "잘못된 인증코드입니다!" }
    private val errorDetail = "$message - $code"

    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail

}
