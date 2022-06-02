package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono

interface RegisterStudentNumberChecker {
    fun valid(studentCode: Int): Mono<Unit>
}
