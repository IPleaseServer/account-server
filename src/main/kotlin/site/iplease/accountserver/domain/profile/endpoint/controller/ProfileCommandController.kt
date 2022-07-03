package site.iplease.accountserver.domain.profile.endpoint.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.domain.profile.data.response.ChangePasswordRequest
import site.iplease.accountserver.domain.profile.data.response.ProfileResponse
import site.iplease.accountserver.domain.profile.service.ProfileCommandService
import site.iplease.accountserver.domain.profile.util.ProfileCommandPreprocessor
import site.iplease.accountserver.domain.profile.util.ProfileConverter

@Validated
@RestController
@RequestMapping("/api/v1/account/profile/command")
class ProfileCommandController(
    private val profileCommandPreprocessor: ProfileCommandPreprocessor,
    private val profileCommandService: ProfileCommandService,
    private val profileConverter: ProfileConverter
) {
    @PutMapping("/password")
    fun changePassword(@RequestHeader("X-Authorization-Id") accountId: Long, @RequestBody request: ChangePasswordRequest): Mono<ResponseEntity<Unit>> =
        profileCommandPreprocessor.validateChangePassword(accountId, request.emailToken)
            .flatMap { profileCommandService.changePassword(accountId, request.newPassword) }
            .map { ResponseEntity.ok(it) }

    @PutMapping
    fun updateMyProfile(@RequestHeader("X-Authorization-Id") accountId: Long, request: UpdateProfileRequest): Mono<ResponseEntity<ProfileResponse>> =
        profileCommandPreprocessor.validateUpdateProfile(accountId, request)
            .flatMap { dto -> profileCommandService.updateProfile(dto, accountId) }
            .flatMap { profile -> profileConverter.toResponse(profile) }
            .map { response -> ResponseEntity.ok(response) }
}