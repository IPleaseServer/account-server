package site.iplease.accountserver.domain.profile.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.data.type.AuthType
import site.iplease.accountserver.global.auth.exception.WrongEmailTokenException
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder
import site.iplease.accountserver.global.common.entity.Account
import site.iplease.accountserver.global.common.repository.AccountRepository

@Component
class ProfileCommandPolicyValidatorImpl(
    private val authTokenDecoder: AuthTokenDecoder,
    private val accountRepository: AccountRepository,
    private val profileConverter: ProfileConverter
): ProfileCommandPolicyValidator {
    override fun validateChangePassword(accountId: Long, emailToken: String, newPassword: String): Mono<Account> =
        authTokenDecoder.decode(AuthTokenDto(token = emailToken))
            .flatMap { dto ->
                if (dto.type == AuthType.EMAIL) dto.data.toMono()
                else Mono.error(WrongEmailTokenException("해당 토큰은 ${dto.type}토큰입니다!"))
            }.flatMap { email -> accountRepository.findByEmail(email) }
            .flatMap { account ->
                if (account.id == accountId) account.toMono()
                else Mono.error(WrongEmailTokenException("해당 토큰은 다른 사용자의 인증토큰입니다! - ${account.id}"))
            }

    override fun validateUpdateProfile(accountId: Long, request: UpdateProfileRequest): Mono<ProfileDto> =
        //프로필 수정 권한을 확인하기위해 이메일 토큰을 검증한다.
        //이메일 토큰이 존재하며, 인자로 받은 accountId와 동일한 계정에 대한 토큰인지 검증한다.
        validateUserToEmailToken(request.emailToken, accountId)
            .flatMap { accountRepository.findById(accountId) } //인자로 받은 accountId를 통해 프로필을 변경할 계정을 찾는다.
            .flatMap { account -> makeProfileDto(request, account) } //계정과 인자로 받은 프로필 변경 요청 정보를 통해 변경될 프로필을 생성한다.

    //이메일 토큰으로 사용자의 인증인가 정보를 검증한다.
    private fun validateUserToEmailToken(emailToken: String, accountId: Long) =
        decodeEmailToken(emailToken)
            .flatMap { email -> accountRepository.findByEmail(email) }
            .flatMap { account ->
                if (account.id == accountId) account.toMono()
                else Mono.error(WrongEmailTokenException("해당 토큰은 다른 사용자의 인증토큰입니다! - ${account.id}"))
            }

    //이메일 토큰을 해석한다.
    private fun decodeEmailToken(emailToken: String) = //TODO auth 도메인에 emailTokenDecoder 만들어서 해당 유틸로 메서드 이전하기
        authTokenDecoder.decode(AuthTokenDto(token = emailToken))
            .flatMap { dto ->
                if (dto.type == AuthType.EMAIL) dto.data.toMono()
                else Mono.error(WrongEmailTokenException("해당 토큰은 ${dto.type}토큰입니다!"))
            }

    private fun makeProfileDto(request: UpdateProfileRequest, account: Account) =
        account.toMono()
            .flatMap { //신규 이메일로 변경될 경우, 이메일토큰을 검증하여 새 이메일을 확인한다.
                val email = if(request.newEmailToken != null) decodeEmailToken(request.newEmailToken) else account.email.toMono()
                email.map { account to it }
            }.flatMap { profileConverter.toDto(request) }//이메일, 기존 계정 정보, 프로필 변경 요청정보를 통해 변경될 프로필을 생성한다.
}