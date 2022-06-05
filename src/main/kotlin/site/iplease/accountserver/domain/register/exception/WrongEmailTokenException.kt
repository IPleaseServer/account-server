package site.iplease.accountserver.domain.register.exception

import site.iplease.accountserver.global.error.IpleaseError

class WrongEmailTokenException(message: String):
    RuntimeException("$ERROR_MESSAGE - $message"), IpleaseError {
    companion object { private const val ERROR_MESSAGE = "잘못된 이메일 토큰입니다!"  }
    private val errorDetail = message

    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail
}
