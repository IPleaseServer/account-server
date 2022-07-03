package site.iplease.accountserver.domain.profile.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.global.common.exception.PolicyViolationException
import site.iplease.accountserver.global.common.type.DepartmentType
import site.iplease.accountserver.global.common.type.PolicyType
import java.net.URI
import javax.imageio.ImageIO

@Component
class ProfilePolicyValidatorImpl: ProfilePolicyValidator {
    override fun validate(profileDto: ProfileDto): Mono<Unit> =
        Unit.toMono()
            .flatMap { validateNamePolicy(profileDto.name) }
            .flatMap { validateProfileImagePolicy(profileDto.profileImage) }
            .flatMap { validateStudentNumber(profileDto.studentNumber) }
            .flatMap { validateDepartment(profileDto.department, profileDto.studentNumber) }


    private fun validateDepartment(department: DepartmentType, studentNumber: Int): Mono<Unit> =
        getClassByStudentNumber(studentNumber).toMono()
            .map { clazz -> department.classes.contains(clazz) }
            .flatMap { isMatch -> //학과와 반이 일치하는가
                if (isMatch) Unit.toMono()
                else Mono.error(PolicyViolationException(PolicyType.DEPARTMENT, "학과와 반이 일치하지 않습니다!"))
            }
    private fun getClassByStudentNumber(studentNumber: Int) = studentNumber%1000/100

    private fun validateStudentNumber(studentNumber: Int): Mono<Unit> =
        Regex("[0-3][1-4][0-2]\\d").toMono()
            .map { regex -> studentNumber.toString().matches(regex) }
            .flatMap { isMatch ->
                if (isMatch) Unit.toMono()
                else Mono.error(PolicyViolationException(PolicyType.STUDENT_NUMBER, "학번이 올바르지 않습니다!"))
            }

    private fun validateProfileImagePolicy(profileImage: URI): Mono<Unit> =
        Unit.toMono()
            .map { ImageIO.read(profileImage.toURL()) != null }
            .onErrorReturn(false)
            .flatMap { isFormed ->
                if (isFormed) Unit.toMono()
                else Mono.error(PolicyViolationException(PolicyType.PROFILE_IMAGE, "프로필 이미지가 올바르지 않습니다!"))
            }

    private fun validateNamePolicy(name: String): Mono<Unit> =
        name.toMono()
            .map { it.isNotEmpty() && it.length <= 5 && it.length >= 2 }
            .flatMap { isMatch ->
                if (isMatch) Unit.toMono()
                else Mono.error(PolicyViolationException(PolicyType.NAME, "이름이 올바르지 않습니다!"))
            }
}