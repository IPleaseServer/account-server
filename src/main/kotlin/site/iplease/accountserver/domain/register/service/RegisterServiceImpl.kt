package site.iplease.accountserver.domain.register.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.entity.Account
import site.iplease.accountserver.global.register.data.type.AccountType
import site.iplease.accountserver.global.register.repository.AccountRepository

@Service
class RegisterServiceImpl(
    private val registerPolicyService: RegisterPolicyService,
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
): RegisterService {
    override fun registerStudent(common: CommonRegisterDto, student: StudentAdditionalRegisterDto): Mono<Long> =
        registerPolicyService.checkCommonPolicy(common)//회원가입 정책을 검사한다.
            .flatMap { registerPolicyService.checkStudentPolicy(student) }
            .map { Account(//회원정보를 구성한다.
                type = AccountType.STUDENT,
                name = common.name, email = common.email, encodedPassword = passwordEncoder.encode(common.password),
                studentNumber = student.studentNumber, department = student.department
            ) }.flatMap { accountRepository.save(it) }//구성한 회원정보를 저장한다.
            .map { it.id }//저장한 회원정보의 id를 반환한다.

    override fun registerTeacher(common: CommonRegisterDto, teacher: TeacherAdditionalRegisterDto): Mono<Long> =
        registerPolicyService.checkCommonPolicy(common)//회원가입 정책을 검사한다.
            .flatMap { registerPolicyService.checkTeacherPolicy(teacher) }
            .map { Account(//회원정보를 구성한다.
                type = AccountType.TEACHER,
                name = common.name, email = common.email, encodedPassword = passwordEncoder.encode(common.password),
            ) }.flatMap { accountRepository.save(it) }//구성한 회원정보를 저장한다.
            .map { it.id }//저장한 회원정보의 id를 반환한다.
}