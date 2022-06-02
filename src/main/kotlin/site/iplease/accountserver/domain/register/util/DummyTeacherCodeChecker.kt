package site.iplease.accountserver.domain.register.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.register.data.type.PolicyType
import site.iplease.accountserver.domain.register.exception.PolicyViolationException

@Component
class DummyTeacherCodeChecker: TeacherCodeChecker {
    override fun valid(teacherCode: String): Mono<Unit> =
        if(teacherCode.startsWith("teacherCode")) Unit.toMono()
        else Mono.error(PolicyViolationException(PolicyType.TEACHER_CODE, "@Dummy교사코드는 항상 teacherCode로 시작해야합니다"))
}