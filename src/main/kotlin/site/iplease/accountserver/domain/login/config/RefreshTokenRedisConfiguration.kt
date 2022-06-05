package site.iplease.accountserver.domain.login.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import site.iplease.accountserver.domain.login.data.entity.RefreshToken

@Configuration
class RefreshTokenRedisConfiguration {
    @Bean //TODO ObjectMapper 빈으로 등록하여 로직 분리
    fun refreshTokenReactiveRedisTemplate(connectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, RefreshToken> =
        ObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())
            .let {  Jackson2JsonRedisSerializer(RefreshToken::class.java).apply { setObjectMapper(it) } }
            .let { (StringRedisSerializer() to it) }
            .let {
                RedisSerializationContext
                    .newSerializationContext<String, RefreshToken>()
                    .key(it.first).value(it.second)
                    .hashKey(it.first).hashValue(it.second)
                    .build()
            }.let { ReactiveRedisTemplate(connectionFactory, it) }
}