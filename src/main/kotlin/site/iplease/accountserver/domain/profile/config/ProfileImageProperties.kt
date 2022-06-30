package site.iplease.accountserver.domain.profile.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("iplease.profile-image")
data class ProfileImageProperties(val defaultUrl: String)