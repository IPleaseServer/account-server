package site.iplease.accountserver.infra.email.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("iplease.email")
data class EmailProperty(val from: String)