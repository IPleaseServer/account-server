package site.iplease.accountserver.domain.register.rule

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto
import site.iplease.accountserver.domain.register.exception.WrongDepartmentOrStudentNumberException
import site.iplease.accountserver.global.common.exception.RuleViolationException
import site.iplease.accountserver.global.common.type.DepartmentType
import site.iplease.accountserver.global.common.type.RuleType

@Component
class StudentRegistrationRuleImpl: StudentRegistrationRule {
    override fun validate(validationDto: StudentRegisterValidationDto): Mono<Unit> =
        validateName(validationDto.name)
            .flatMap { validateStudentNumber(validationDto.studentNumber) }
            .flatMap { validateDepartmentMatchStudentNumber(validationDto.studentNumber, validationDto.department) }

    private fun validateName(name: String): Mono<Unit> =
        if(name.length < 2 || name.length > 5) Mono.error(RuleViolationException(RuleType.REGISTER_NAME, "이름은 2-5자여야합니다!"))
        else Unit.toMono()

    private fun validateStudentNumber(studentNumber: Int): Mono<Unit> =
        "$studentNumber".matches(Regex("^[0-3][1-4][0-2]\\d\$")).toMono()
            .flatMap { isMatch ->
                if (isMatch) Unit.toMono()
                else Mono.error(RuleViolationException(RuleType.REGISTER_STUDENT_NUMBER, "잘못된 학번입니다!"))
            }

    private fun validateDepartmentMatchStudentNumber(
        studentNumber: Int,
        department: DepartmentType): Mono<Unit> =
        (studentNumber%1000/100).toMono()
            .map { department.classes.contains(it) to it }
            .flatMap {
                if(it.first) Unit.toMono()
                else Mono.error(WrongDepartmentOrStudentNumberException("${it.second}반은 $department 학과의 반이 아닙니다!"))
            }
}