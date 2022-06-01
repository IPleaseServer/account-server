package site.iplease.accountserver.domain.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "auth")
data class AuthProperties (
    val redisKeyPrefix: String,
    val expireSeconds: Long,
    val emailProperties: AuthEmailProperties,
    val jwtProperties: AuthJwtProperties
) {
    data class AuthEmailProperties(
        val emailTitle: String,
        val templatePath: String
    )
    data class AuthJwtProperties(
        val issuer: String,
        val expireSecond: Long,
        val secret: String
    )
}
