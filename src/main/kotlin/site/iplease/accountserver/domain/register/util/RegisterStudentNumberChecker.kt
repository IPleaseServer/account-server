package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono

interface RegisterStudentNumberChecker {
    fun valid(studentNumber: Int): Mono<Unit>
}
