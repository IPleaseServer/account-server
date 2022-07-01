package site.iplease.accountserver.domain.profile.endpoint.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.domain.profile.data.response.ChangePasswordRequest
import site.iplease.accountserver.domain.profile.data.response.ProfileResponse
import site.iplease.accountserver.domain.profile.service.ProfileCommandService
import site.iplease.accountserver.domain.profile.util.ProfileCommandPolicyValidator

@Validated
@RestController
@RequestMapping("/api/v1/account/profile/secure")
class SecureProfileController(
    private val profileCommandPolicyValidator: ProfileCommandPolicyValidator,
    private val profileCommandService: ProfileCommandService
) {
    @PutMapping("/password")
    fun changePassword(@RequestHeader("X-Authorization-Id") accountId: Long, @RequestBody request: ChangePasswordRequest): Mono<ResponseEntity<Unit>> =
        profileCommandPolicyValidator.validateChangePassword(accountId, request.emailToken)
            .flatMap { profileCommandService.changePassword(accountId, request.newPassword) }
            .map { ResponseEntity.ok(it) }

    @PutMapping
    fun updateMyProfile(@RequestHeader("X-Authorization-Id") accountId: Long, request: UpdateProfileRequest): Mono<ResponseEntity<ProfileResponse>> =
        profileCommandPolicyValidator.validateUpdateProfile(accountId, request)
            .flatMap { dto -> profileCommandService.updateProfile(dto, accountId) }
            .map { profile -> profile.toResponse() }
            .map { response -> ResponseEntity.ok(response) }
}