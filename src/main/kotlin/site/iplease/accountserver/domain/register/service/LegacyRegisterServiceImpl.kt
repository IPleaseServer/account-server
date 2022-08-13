package site.iplease.accountserver.domain.register.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.util.ProfileConverter
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.global.common.repository.AccountRepository
import site.iplease.accountserver.global.common.type.AccountType
import site.iplease.accountserver.global.common.type.PermissionType

@Service
class LegacyRegisterServiceImpl(
    private val registerPolicyService: RegisterPolicyService,
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val profileConverter: ProfileConverter
): LegacyRegisterService {
    override fun registerStudent(common: CommonRegisterDto, student: StudentAdditionalRegisterDto): Mono<Long> =
        registerPolicyService.checkCommonPolicy(common)//회원가입 정책을 검사한다.
            .flatMap { registerPolicyService.checkStudentPolicy(student) }
            .flatMap { profileConverter.toEntity(AccountType.STUDENT, PermissionType.USER, passwordEncoder.encode(common.password), common, student) }
            .flatMap { accountRepository.save(it) }//구성한 회원정보를 저장한다.
            .map { it.id }//저장한 회원정보의 id를 반환한다.

    override fun registerTeacher(common: CommonRegisterDto, teacher: TeacherAdditionalRegisterDto): Mono<Long> =
        registerPolicyService.checkCommonPolicy(common)//회원가입 정책을 검사한다.
            .flatMap { registerPolicyService.checkTeacherPolicy(teacher) }
            .flatMap { profileConverter.toEntity(AccountType.TEACHER, PermissionType.OPERATOR, passwordEncoder.encode(common.password), common, teacher) }
            .flatMap { accountRepository.save(it) }//구성한 회원정보를 저장한다.
            .map { it.id }//저장한 회원정보의 id를 반환한다.
}