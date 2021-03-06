package site.iplease.accountserver.domain.login.repository

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.login.data.entity.RefreshToken

interface RefreshTokenRepository {
    fun insert(authCode: RefreshToken): Mono<Void>
    fun select(token: String): Mono<RefreshToken>
    fun selectByAccountId(id: Long): Mono<RefreshToken>
    fun exist(token: String): Mono<Boolean>
    fun delete(token: String): Mono<Void>
    fun deleteByAccountId(id: Long): Mono<Void>
}
