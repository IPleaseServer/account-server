package site.iplease.accountserver.domain.profile.endpoint.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.dto.ProfileDto
import site.iplease.accountserver.domain.profile.response.CommonProfileResponse
import site.iplease.accountserver.domain.profile.response.ProfileResponse
import site.iplease.accountserver.domain.profile.response.StudentProfileResponse
import site.iplease.accountserver.domain.profile.response.TeacherProfileResponse
import site.iplease.accountserver.domain.profile.service.ProfileService
import site.iplease.accountserver.global.common.type.AccountType

@Validated
@RestController
@RequestMapping("/api/v1/account/profile")
class ProfileController(
    private val profileService: ProfileService
) {
    @GetMapping
    fun getMyProfile(@RequestHeader authorization: String): Mono<ResponseEntity<ProfileResponse>> =
        authorization.substring("Bearer ".length)//액세스 토큰을 추출한다.
            .let { profileService.getProfileByAccessToken(it) }
            .map { it.toResponse() }
            .map { ResponseEntity.ok(it) }
    @GetMapping("/{accountId}")
    fun getProfileByAccountId(@PathVariable accountId: Long): Mono<ResponseEntity<ProfileResponse>> =
        profileService.getProfileByAccountId(accountId)
            .map { it.toResponse() }
            .map { ResponseEntity.ok(it) }

    private fun ProfileDto.toResponse() =
        ProfileResponse(
            type = type,
            common = CommonProfileResponse(
                accountId = accountId,
                name = name,
                email = email,
                profileImage = profileImage.toString()
            ),
            teacher = if(type == AccountType.TEACHER) TeacherProfileResponse() else null,
            student = if(type == AccountType.STUDENT) StudentProfileResponse(
                studentNumber = studentNumber,
                department = department
            ) else null
        )
}