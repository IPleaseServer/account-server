package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.global.common.entity.Account

interface ProfileCommandPolicyValidator {
    fun validateChangePassword(accountId: Long, emailToken: String, newPassword: String): Mono<Account>
}