package site.iplease.accountserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import site.iplease.accountserver.domain.auth.config.AuthProperties

@SpringBootApplication
@EnableConfigurationProperties(AuthProperties::class)
class AccountServerApplication

fun main(args: Array<String>) {
    runApplication<AccountServerApplication>(*args)
}
