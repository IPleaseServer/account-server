package site.iplease.accountserver.domain.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import site.iplease.accountserver.domain.auth.entity.AuthCode

@Configuration
class AuthCodeRedisConfiguration {
    @Bean
    fun reactiveRedisTemplate(connectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, AuthCode> =
        (StringRedisSerializer() to Jackson2JsonRedisSerializer(AuthCode::class.java))
            .let {
                RedisSerializationContext
                    .newSerializationContext<String, AuthCode>()
                    .key(it.first).value(it.second)
                    .hashKey(it.first).hashValue(it.second)
                    .build()
            }.let { ReactiveRedisTemplate(connectionFactory, it) }
}