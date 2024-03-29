package site.iplease.accountserver.domain.profile.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.util.ProfileConverter
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder
import site.iplease.accountserver.global.common.exception.UnknownAccountException
import site.iplease.accountserver.global.login.util.atomic.AccessTokenDecoder
import site.iplease.accountserver.global.common.repository.AccountRepository

@Service
class ProfileServiceImpl(
    private val accountRepository: AccountRepository,
    private val accessTokenDecoder: AccessTokenDecoder,
    private val profileConverter: ProfileConverter,
    private val authTokenDecoder: AuthTokenDecoder
): ProfileService {
    override fun getProfileByAccessToken(accessToken: String): Mono<ProfileDto> =
        accessTokenDecoder.decode(accessToken)//액세스토큰을 검증하여, accountId를 가져온다.
            .flatMap { getAccountByAccountId(it) }//accountId를 통해 계정의 프로필을 가져온다.
            .flatMap { account -> profileConverter.toDto(account) }//프로필을 반환한다.

    override fun getProfileByAccountId(accountId: Long): Mono<ProfileDto> =
        getAccountByAccountId(accountId)//accountId를 통해 계정의 프로필을 가져온다.
            .flatMap { account -> profileConverter.toDto(account) }//프로필을 반환한다.

    override fun existProfileByAccessToken(accessToken: String): Mono<Boolean> =
        accessTokenDecoder.decode(accessToken)
            .flatMap { existAccountByAccountId(it) }
            .onErrorReturn(false)

    override fun existsProfileByEmail(email: String): Mono<Boolean> =
        existAccountByEmail(email)
            .onErrorReturn(false)

    override fun getProfileByEmailToken(emailToken: String): Mono<ProfileDto> =
        authTokenDecoder.decode(AuthTokenDto(token = emailToken))
            .flatMap { getAccountByEmail(it.data) }
            .flatMap { account -> profileConverter.toDto(account) }

    private fun getAccountByEmail(email: String) =
        existsProfileByEmail(email)
            .flatMap { isExists ->
                if(isExists) accountRepository.findByEmail(email)
                else Mono.error(UnknownAccountException("이메일이 ${email}인 계정을 찾을 수 없습니다!"))
            }

    private fun getAccountByAccountId(accountId: Long) =
        existAccountByAccountId(accountId)
            .flatMap { isExists ->
                if(isExists) accountRepository.findById(accountId)
                else Mono.error(UnknownAccountException("id가 ${accountId}인 계정을 찾을 수 없습니다!"))
            }

    private fun existAccountByAccountId(accountId: Long) =
        accountRepository.existsById(accountId)

    private fun existAccountByEmail(email: String) =
        accountRepository.existsByEmail(email)
}
