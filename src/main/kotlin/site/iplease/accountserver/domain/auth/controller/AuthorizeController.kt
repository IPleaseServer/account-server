package site.iplease.accountserver.domain.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import site.iplease.accountserver.domain.auth.response.AuthTokenResponse
import site.iplease.accountserver.domain.auth.validate.AuthCode
import javax.validation.constraints.Email

@RestController
@RequestMapping("/api/v1/account/auth")
class AuthorizeController {
    @GetMapping("/{authCode}")
    fun authorize(@PathVariable @AuthCode authCode: String): ResponseEntity<AuthTokenResponse> {
        TODO("Not yet implemented")
        //DataStore에서 인증코드에 매핑된 Type(인증대상 종류 := Class)과 Data(인증대상 정보 := Instance)를 가져온다.
        //가져온 Type과 Data로 인증토큰을 생성한다.
        //생성한 인증토큰을 반환한다.
    }

    @PostMapping("/email")
    fun authorizeEmail(@RequestParam @Email email: String): ResponseEntity<Unit> {
        TODO("Not yet implemented")
        //인증코드를 발급한다.
        //인증코드를 이메일로 보낸다.
        //인증코드와 이메일을 매핑하여 DataStore에 저장한다.
        //Payload가 비어있는 ResponseEntity(Status=OK)를 반환한다.
    }
}