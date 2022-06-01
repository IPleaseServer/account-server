package site.iplease.accountserver.domain.auth.repository

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.auth.config.AuthProperties
import site.iplease.accountserver.domain.auth.data.entity.AuthCode

@Component
class RedisAuthCodeRepository(
    private val redisTemplate: ReactiveRedisTemplate<String, AuthCode>,
    private val authProperties: AuthProperties
): AuthCodeRepository {
    override fun insert(authCode: AuthCode): Mono<Void> =
        redisTemplate.opsForValue()
            .set(formatKey(authCode.code), authCode)
            .then()

    override fun select(code: String): Mono<AuthCode> =
        redisTemplate.opsForValue().get(formatKey(code))

    override fun exist(code: String): Mono<Boolean> =
        redisTemplate.hasKey(formatKey(code))

    override fun delete(code: String): Mono<Void> =
        redisTemplate.delete(code).then()

    private fun formatKey(code: String) = "${authProperties.redisKeyPrefix}_$code"
}