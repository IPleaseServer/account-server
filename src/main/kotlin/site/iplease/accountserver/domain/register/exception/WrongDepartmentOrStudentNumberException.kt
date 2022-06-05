package site.iplease.accountserver.domain.register.exception

import site.iplease.accountserver.global.error.IpleaseError

class WrongDepartmentOrStudentNumberException(message: String):
    RuntimeException("$ERROR_MESSAGE - $message"), IpleaseError {
    companion object { private const val ERROR_MESSAGE = "잘못된 학번 또는 학과입니다!"  }
    private val errorDetail = message

    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail
}