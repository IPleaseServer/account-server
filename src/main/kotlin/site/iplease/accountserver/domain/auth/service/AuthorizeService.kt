package site.iplease.accountserver.domain.auth.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto

interface AuthorizeService {
    //DataStore에서 인증코드에 매핑된 Type(인증대상 종류 := Class)과 Data(인증대상 정보 := Instance)를 가져온다.
    //가져온 Type과 Data로 인증토큰을 생성한다.
    //생성한 인증토큰을 반환한다.
    fun authorize(code: String): Mono<AuthTokenDto>

    //인증코드를 발급한다.
    //인증코드를 이메일로 보낸다.
    //인증코드와 이메일을 매핑하여 DataStore에 저장한다.
    //Payload가 비어있는 ResponseEntity(Status=OK)를 반환한다.
    fun authorizeEmail(email: String): Mono<Unit>
}