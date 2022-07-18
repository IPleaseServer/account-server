package site.iplease.accountserver.domain.register.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.domain.register.util.atomic.RegisterEmailValidator
import site.iplease.accountserver.domain.register.util.atomic.RegisterStudentNumberValidator
import site.iplease.accountserver.domain.register.util.atomic.TeacherCodeValidator

@Service //외부 상태에 영향을 받는 가입조건을 검사한다.
class RegisterPolicyServiceImpl(
    private val registerEmailValidator: RegisterEmailValidator,
    private val registerStudentNumberValidator: RegisterStudentNumberValidator,
    private val teacherCodeValidator: TeacherCodeValidator,
): RegisterPolicyService {
    override fun checkCommonPolicy(common: CommonRegisterDto): Mono<Unit> =
        registerEmailValidator.valid(common.email)
    override fun checkStudentPolicy(student: StudentAdditionalRegisterDto): Mono<Unit> =
        registerStudentNumberValidator.valid(student.studentNumber)
    override fun checkTeacherPolicy(teacher: TeacherAdditionalRegisterDto): Mono<Unit> =
        teacherCodeValidator.valid(teacher.teacherCode)
}