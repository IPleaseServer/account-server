package site.iplease.accountserver.domain.register.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.domain.register.util.RegisterEmailChecker
import site.iplease.accountserver.domain.register.util.RegisterStudentNumberChecker
import site.iplease.accountserver.domain.register.util.TeacherCodeChecker

@Service //외부 상태에 영향을 받는 가입조건을 검사한다.
class RegisterPolicyServiceImpl(
    private val registerEmailChecker: RegisterEmailChecker,
    private val registerStudentNumberChecker: RegisterStudentNumberChecker,
    private val teacherCodeChecker: TeacherCodeChecker,
): RegisterPolicyService {
    override fun checkCommonPolicy(common: CommonRegisterDto): Mono<Unit> =
        registerEmailChecker.valid(common.email)
    override fun checkStudentPolicy(student: StudentAdditionalRegisterDto): Mono<Unit> =
        registerStudentNumberChecker.valid(student.studentNumber)
    override fun checkTeacherPolicy(teacher: TeacherAdditionalRegisterDto): Mono<Unit> =
        teacherCodeChecker.valid(teacher.teacherCode)
}