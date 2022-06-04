package site.iplease.accountserver.domain.register.util.atomic

import reactor.core.publisher.Mono

interface TeacherCodeChecker {
    fun valid(teacherCode: String): Mono<Unit>
}
