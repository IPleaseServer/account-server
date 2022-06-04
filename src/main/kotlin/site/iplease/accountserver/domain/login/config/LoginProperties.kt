package site.iplease.accountserver.domain.login.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "login")
data class LoginProperties (
    val refreshProperties: RefreshTokenProperties,
    val accessProperties: AccessTokenProperties,
) {
    data class AccessTokenProperties (
        val expireSeconds: Long,
        val issuer: String,
        val secret: String
    )

    data class RefreshTokenProperties (
        val expireSeconds: Long
    )
}