package site.iplease.accountserver.domain.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "auth")
data class AuthProperties (
    val redisKeyPrefix: String,
    val emailProperties: AuthEmailProperty
) {
    data class AuthEmailProperty (
        val emailTitle: String,
        val templatePath: String
    )
}
