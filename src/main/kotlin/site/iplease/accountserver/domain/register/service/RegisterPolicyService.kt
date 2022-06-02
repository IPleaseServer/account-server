package site.iplease.accountserver.domain.register.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto

interface RegisterPolicyService {
    fun checkCommonPolicy(common: CommonRegisterDto): Mono<Unit>
    fun checkStudentPolicy(student: StudentAdditionalRegisterDto): Mono<Unit>
    fun checkTeacherPolicy(teacher: TeacherAdditionalRegisterDto): Mono<Unit>
}
