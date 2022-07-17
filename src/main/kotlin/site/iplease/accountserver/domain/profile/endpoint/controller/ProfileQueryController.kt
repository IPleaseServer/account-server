package site.iplease.accountserver.domain.profile.endpoint.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.response.ProfileExistsResponse
import site.iplease.accountserver.domain.profile.data.response.ProfileResponse
import site.iplease.accountserver.domain.profile.service.ProfileService
import site.iplease.accountserver.domain.profile.util.ProfileConverter

@Validated
@RestController
@RequestMapping("/api/v1/account/profile/query")
class ProfileQueryController(
    private val profileService: ProfileService,
    private val profileConverter: ProfileConverter
) {
    @GetMapping("/access-token/{token}")
    fun getMyProfile(@PathVariable token: String): Mono<ResponseEntity<ProfileResponse>> =
        profileService.getProfileByAccessToken(token)
            .flatMap { profile -> profileConverter.toResponse(profile) }
            .map { response -> ResponseEntity.ok(response) }

    @GetMapping("/id/{id}")
    fun getProfileByAccountId(@PathVariable id: Long): Mono<ResponseEntity<ProfileResponse>> =
        profileService.getProfileByAccountId(id)
            .flatMap { profile -> profileConverter.toResponse(profile) }
            .map { response -> ResponseEntity.ok(response) }

    @GetMapping("/access-token/{token}/exists")
    fun existsMyProfile(@PathVariable token: String): Mono<ResponseEntity<ProfileExistsResponse>> =
        profileService.existProfileByAccessToken(token)
            .map { bool ->  ProfileExistsResponse(bool) }
            .map { response -> ResponseEntity.ok(response) }

    //이메일을 통해 계정 존재 여부를 확인한다.
    @GetMapping("/email/{email}/exists")
    fun existsAccountByEmail(@PathVariable email: String): Mono<ResponseEntity<ProfileExistsResponse>> =
        profileService.existsProfileByEmail(email) //계정 존재 여부를 검사한다.
            .map { bool ->  ProfileExistsResponse(bool) } //반환된 검사결과를 Response로 변환한다.
            .map { response -> ResponseEntity.ok(response) } //Response를 ResponseEntity로 변환하여 반환한다.
}