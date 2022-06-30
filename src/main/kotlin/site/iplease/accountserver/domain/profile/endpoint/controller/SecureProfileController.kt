package site.iplease.accountserver.domain.profile.endpoint.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.domain.profile.data.response.ChangePasswordRequest
import site.iplease.accountserver.domain.profile.data.response.ProfileResponse
import site.iplease.accountserver.domain.profile.service.ProfileCommandService
import site.iplease.accountserver.domain.profile.util.ProfileConverter

@Validated
@RestController
@RequestMapping("/api/v1/account/profile/secure")
class SecureProfileController(
    private val profileConverter: ProfileConverter,
    private val profileCommandService: ProfileCommandService
) {
    @PutMapping("/password")
    fun changePassword(@RequestHeader("X-Authorization-Id") accountId: Long, @RequestBody request: ChangePasswordRequest): Mono<ResponseEntity<Unit>> =
        profileCommandService.changePassword(accountId, request.emailToken, request.newPassword)
            .map { ResponseEntity.ok(it) }

    @PutMapping
    fun updateMyProfile(@RequestHeader("X-Authorization-Id") accountId: Long, request: UpdateProfileRequest): Mono<ResponseEntity<ProfileResponse>> =
        profileConverter.toDto(request)
            .flatMap { dto -> profileCommandService.updateProfile(dto) }
            .map { profile -> profile.toResponse() }
            .map { response -> ResponseEntity.ok(response) }
}