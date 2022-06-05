package site.iplease.accountserver.domain.login.repository

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.login.config.LoginProperties
import site.iplease.accountserver.domain.login.data.entity.RefreshToken
import java.time.Duration

@Component
class RefreshTokenRepositoryImpl(
    private val redisTemplate: ReactiveRedisTemplate<String, RefreshToken>,
    private val loginProperties: LoginProperties
): RefreshTokenRepository {
    private val duration = lazy{ Duration.ofSeconds(loginProperties.refreshProperties.expireSeconds) }

    override fun insert(authCode: RefreshToken): Mono<Void> =
        redisTemplate.opsForValue()
            .set(format(authCode.token), authCode, duration.value)
            .flatMap {
                redisTemplate.opsForValue()
                    .set(formatIdTable(authCode.accountId), authCode, duration.value)
            }.then()

    override fun select(token: String): Mono<RefreshToken> =
        redisTemplate.opsForValue()
            .get(format(token))

    override fun selectByAccountId(id: Long): Mono<RefreshToken> =
        redisTemplate.opsForValue()
            .get(formatIdTable(id))

    override fun exist(token: String): Mono<Boolean> =
        redisTemplate.hasKey(format(token))

    override fun delete(token: String): Mono<Void> =
        select(token).flatMap {
            redisTemplate.delete(format(token), formatIdTable(it.accountId))
                .then()
        }

    override fun deleteByAccountId(id: Long): Mono<Void> =
        selectByAccountId(id)
            .flatMap { delete(it.token) }

    private fun format(token: String) = "refresh_token_$token"
    private fun formatIdTable(accountId: Long) = "refresh_token_by_account_id_$accountId"
}