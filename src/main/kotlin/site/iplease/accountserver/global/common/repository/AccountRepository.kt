package site.iplease.accountserver.global.common.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import site.iplease.accountserver.global.common.entity.Account

interface AccountRepository: ReactiveCrudRepository<Account, Long> {
    fun findByEmail(email: String): Mono<Account>
    fun existsByEmail(email: String): Mono<Boolean>
    fun existsByStudentNumber(studentNumber: Int): Mono<Boolean>
}
