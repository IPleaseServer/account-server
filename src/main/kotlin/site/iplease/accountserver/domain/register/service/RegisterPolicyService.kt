package site.iplease.accountserver.domain.register.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto

//외부 상태에 영향을 받는 가입조건을 검사한다.
interface RegisterPolicyService {
    fun checkCommonPolicy(common: CommonRegisterDto): Mono<Unit>
    fun checkStudentPolicy(student: StudentAdditionalRegisterDto): Mono<Unit>
    fun checkTeacherPolicy(teacher: TeacherAdditionalRegisterDto): Mono<Unit>
}
