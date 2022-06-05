package site.iplease.accountserver.global.common.exception

import site.iplease.accountserver.global.common.type.PolicyType
import site.iplease.accountserver.global.error.IpleaseError

class PolicyViolationException(private val policy: PolicyType, message: String):
RuntimeException("${policy.displayName} $ERROR_MESSAGE - $message"), IpleaseError {
    companion object { private const val ERROR_MESSAGE = "정책을 위반하였습니다!"  }
    private val errorDetail = message

    override fun getErrorMessage(): String = "${policy.displayName} $ERROR_MESSAGE"
    override fun getErrorDetail(): String = errorDetail
}