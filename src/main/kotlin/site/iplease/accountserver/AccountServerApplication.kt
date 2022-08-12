package site.iplease.accountserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import site.iplease.accountserver.domain.auth.config.AuthProperties
import site.iplease.accountserver.domain.login.config.LoginProperties
import site.iplease.accountserver.domain.profile.config.ProfileImageProperties
import site.iplease.accountserver.infra.email.config.EmailProperty

@SpringBootApplication
@EnableConfigurationProperties(AuthProperties::class, LoginProperties::class, ProfileImageProperties::class, EmailProperty::class)
class AccountServerApplication

fun main(args: Array<String>) {
    runApplication<AccountServerApplication>(*args)
}
