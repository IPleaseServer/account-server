package site.iplease.accountserver.domain.register.util.atomic

import reactor.core.publisher.Mono

interface RegisterStudentNumberValidator {
    fun valid(studentNumber: Int): Mono<Unit>
}
