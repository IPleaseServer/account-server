package site.iplease.accountserver.domain.login.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.login.data.dto.LoginTokenDto
import site.iplease.accountserver.domain.login.util.atomic.*
import site.iplease.accountserver.domain.register.data.entity.Account
import site.iplease.accountserver.global.register.repository.AccountRepository

@Service
class LoginServiceImpl(
    private val accountRepository: AccountRepository,
    private val refreshTokenRemover: RefreshTokenRemover,
    private val accessTokenEncoder: AccessTokenEncoder,
    private val accessTokenDecoder: AccessTokenDecoder,
    private val refreshTokenEncoder: RefreshTokenEncoder,
    private val refreshTokenDecoder: RefreshTokenDecoder,
    private val loginDataDecoder: LoginDataDecoder
): LoginService {
    //재발급 토큰을 검사하여, 정보에 해당하는 유저를 가져온다.
    //가져온 유저의 정보를 통해 로그인토큰을 생성한다.
    //작성한 login token을 반환한다.
    override fun login(email: String, password: String): Mono<LoginTokenDto> =
        getAccount(email, password)
            .flatMap { deleteRefreshToken(it.id).map { _-> it } }
            .flatMap { generateLoginToken(it) }

    //재발급 토큰을 검사하여, 정보에 해당하는 유저를 가져온다.
    //가져온 유저의 정보를 통해 로그인토큰을 생성한다.
    //작성한 login token을 반환한다.
    override fun refreshLogin(refreshToken: String): Mono<LoginTokenDto> =
        getAccount(refreshToken)
            .flatMap { deleteRefreshToken(refreshToken).map { _-> it } }
            .flatMap { generateLoginToken(it) }

    //액세스 토큰을 검사하여, 정보에 해당하는 유저를 가져온다.
    //유저의 정보를 통해 재발급 토큰을 삭제한다.
    override fun logout(accessToken: String): Mono<Unit> =
        accessTokenDecoder.decode(accessToken)
            .flatMap { refreshTokenRemover.removeByAccountId(it) }
            .map {  }

    private fun deleteRefreshToken(refreshToken: String): Mono<String> =
        refreshTokenRemover.remove(refreshToken)

    private fun deleteRefreshToken(accountId: Long): Mono<Long> =
        refreshTokenRemover.removeByAccountId(accountId)

    private fun getAccount(email: String, password: String) =
        loginDataDecoder.decode(email, password)
            .flatMap { accountRepository.findById(it) }

    private fun getAccount(refreshToken: String) =
        refreshTokenDecoder.decode(refreshToken)
            .flatMap { accountRepository.findById(it) }

    //가져온 유저의 공개정보(Id, Role)를 통해 access token을 작성한다.
    //랜덤 알고리즘을 통해 refresh token을 작성한다.
    //작성한 refresh token을 유저의 공개정보와 매핑하여 DataStore에 일정시간 저장한다.
    //작성한 access token과 refresh token으로 login토큰을 작성한다.
    private fun generateLoginToken(account: Account): Mono<LoginTokenDto> =
        accessTokenEncoder.generate(account)
            .flatMap { access ->
                refreshTokenEncoder.encode(account).map { refresh -> access to refresh }
            }.map { LoginTokenDto(it.first, it.second) }
}