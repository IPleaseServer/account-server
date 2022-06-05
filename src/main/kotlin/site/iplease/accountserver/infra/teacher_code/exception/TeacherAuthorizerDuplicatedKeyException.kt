package site.iplease.accountserver.infra.teacher_code.exception

import site.iplease.accountserver.global.error.IpleaseError

class TeacherAuthorizerDuplicatedKeyException(key: String, message: String):
    RuntimeException("$ERROR_MESSAGE - $message - $key"), IpleaseError {
    companion object { private const val ERROR_MESSAGE = "이미 교사인증기에 해당키로 매핑된 정보가 존재합니다!" }
    private val errorDetail = "$message - $key"

    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail
}
