package site.iplease.accountserver.domain.profile.service

import reactor.core.publisher.Mono

interface ProfileCommandService {
    fun changePassword(accountId: Long, emailToken: String, newPassword: String): Mono<Unit>

}
