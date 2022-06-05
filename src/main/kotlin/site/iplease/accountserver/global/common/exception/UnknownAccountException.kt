package site.iplease.accountserver.global.common.exception

import site.iplease.accountserver.global.error.IpleaseError

class UnknownAccountException(message: String):
    RuntimeException("$ERROR_MESSAGE - $message"), IpleaseError {
    companion object { private const val ERROR_MESSAGE = "계정을 찾을 수 없습니다!"  }
    private val errorDetail = message

    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail
}
