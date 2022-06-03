package site.iplease.accountserver.domain.login.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.login.data.request.LoginRequest
import site.iplease.accountserver.domain.login.data.request.RefreshLoginRequest
import site.iplease.accountserver.domain.login.data.response.LoginTokenResponse
import site.iplease.accountserver.domain.login.service.LoginService
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/v1/account/login")
class LoginController(
    private val loginService: LoginService
) {
    //로그인토큰 생성로직
    //-가져온 유저의 공개정보(Id, Role)를 통해 access token을 작성한다.
    //-랜덤 알고리즘을 통해 refresh token을 작성한다.
    //-작성한 refresh token을 유저의 공개정보와 매핑하여 DataStore에 일정시간 저장한다.
    //-작성한 access token과 refresh token으로 login토큰을 작성한다.

    //재발급 토큰을 검사하여, 정보에 해당하는 유저를 가져온다.
    //가져온 유저의 정보를 통해 로그인토큰을 생성한다.
    //작성한 login token을 반환한다.
    @PostMapping
    fun login(@RequestBody @Valid request: LoginRequest): Mono<ResponseEntity<LoginTokenResponse>> =
        loginService.login(request.email, request.password)
            .map { LoginTokenResponse(accessToken = it.accessToken, refreshToken = it.refreshToken) }
            .map { ResponseEntity.ok(it) }

    //재발급 토큰을 검사하여, 정보에 해당하는 유저를 가져온다.
    //가져온 유저의 정보를 통해 로그인토큰을 생성한다.
    //작성한 login token을 반환한다.
    @PostMapping("/refresh")
    fun refreshLogin(@RequestBody @Valid request: RefreshLoginRequest): Mono<ResponseEntity<LoginTokenResponse>> =
        loginService.refreshLogin(request.refreshToken)
            .map { LoginTokenResponse(accessToken = it.accessToken, refreshToken = it.refreshToken) }
            .map { ResponseEntity.ok(it) }
}