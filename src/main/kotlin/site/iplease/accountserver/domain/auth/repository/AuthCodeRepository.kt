package site.iplease.accountserver.domain.auth.repository

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.auth.data.entity.AuthCode

interface AuthCodeRepository {
    fun insert(authCode: AuthCode): Mono<Void>
    fun select(code: String): Mono<AuthCode>
    fun exist(code: String): Mono<Boolean>
}
