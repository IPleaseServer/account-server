package site.iplease.accountserver.domain.login.config

import com.fasterxml.jackson.databind.ObjectMapper
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
    @Bean
    fun refreshTokenReactiveRedisTemplate(connectionFactory: ReactiveRedisConnectionFactory, objectMapper: ObjectMapper): ReactiveRedisTemplate<String, RefreshToken> =
        Jackson2JsonRedisSerializer(RefreshToken::class.java).apply { setObjectMapper(objectMapper) }
            .let { (StringRedisSerializer() to it) }
            .let {
                RedisSerializationContext
                    .newSerializationContext<String, RefreshToken>()
                    .key(it.first).value(it.second)
                    .hashKey(it.first).hashValue(it.second)
                    .build()
            }.let { ReactiveRedisTemplate(connectionFactory, it) }
}