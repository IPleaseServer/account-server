package site.iplease.accountserver.domain.profile.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.dto.ProfileDto
import site.iplease.accountserver.global.common.entity.Account
import site.iplease.accountserver.global.common.exception.UnknownAccountException
import site.iplease.accountserver.global.login.util.atomic.AccessTokenDecoder
import site.iplease.accountserver.global.common.repository.AccountRepository
import java.net.URI

@Service
class ProfileServiceImpl(
    private val accountRepository: AccountRepository,
    private val accessTokenDecoder: AccessTokenDecoder
): ProfileService {
    override fun getProfileByAccessToken(accessToken: String): Mono<ProfileDto> =
        accessTokenDecoder.decode(accessToken)//액세스토큰을 검증하여, accountId를 가져온다.
            .flatMap { getAccountByAccountId(it) }//accountId를 통해 계정의 프로필을 가져온다.
            .map { it.toProfileDto() }//프로필을 반환한다.

    override fun getProfileByAccountId(accountId: Long): Mono<ProfileDto> =
        getAccountByAccountId(accountId)//accountId를 통해 계정의 프로필을 가져온다.
            .map { it.toProfileDto() }//프로필을 반환한다.

    override fun existProfileByAccessToken(accessToken: String): Mono<Boolean> =
        accessTokenDecoder.decode(accessToken)
            .flatMap { existAccountByAccountId(it) }
            .onErrorReturn(false)

    private fun getAccountByAccountId(accountId: Long) =
        existAccountByAccountId(accountId)
            .flatMap { isExists ->
                if(isExists) accountRepository.findById(accountId)
                else Mono.error(UnknownAccountException("id가 ${accountId}인 계정을 찾을 수 없습니다!"))
            }

    private fun existAccountByAccountId(accountId: Long) =
        accountRepository.existsById(accountId)

    private fun Account.toProfileDto() =
        ProfileDto(
            type = type, accountId = id, permission = permission,
            name = name, email = email, profileImage = URI("127.0.0.1"), //TODO Account에 ProfileImage관련 로직 추가
            studentNumber = studentNumber, department = department
        )
}
