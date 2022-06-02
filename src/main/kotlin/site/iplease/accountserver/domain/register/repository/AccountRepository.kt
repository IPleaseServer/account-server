package site.iplease.accountserver.domain.register.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import site.iplease.accountserver.domain.register.data.entity.Account

interface AccountRepository: ReactiveCrudRepository<Account, Long> {

}
