package site.iplease.accountserver.domain.auth.config

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
import site.iplease.accountserver.domain.auth.data.entity.AuthCode

@Configuration
class AuthCodeRedisConfiguration {
    @Bean //TODO ObjectMapper 빈으로 등록하여 로직 분리
    fun reactiveRedisTemplate(connectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, AuthCode> =
        ObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())
            .let {  Jackson2JsonRedisSerializer(AuthCode::class.java).apply { setObjectMapper(it) } }
            .let { (StringRedisSerializer() to it) }
            .let {
                RedisSerializationContext
                    .newSerializationContext<String, AuthCode>()
                    .key(it.first).value(it.second)
                    .hashKey(it.first).hashValue(it.second)
                    .build()
            }.let { ReactiveRedisTemplate(connectionFactory, it) }
}