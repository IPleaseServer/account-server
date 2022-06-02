package site.iplease.accountserver.domain.register.util.atomic

import reactor.core.publisher.Mono

interface RegisterStudentNumberChecker {
    fun valid(studentNumber: Int): Mono<Unit>
}
