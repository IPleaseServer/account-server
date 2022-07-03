package site.iplease.accountserver.domain.profile.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.domain.profile.data.response.ChangePasswordRequest
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.util.atomic.EmailTokenValidator
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder
import site.iplease.accountserver.global.common.util.AccountOwnerValidator

@Component
class ProfileCommandPreprocessorImpl(
    private val authTokenDecoder: AuthTokenDecoder,
    private val profileConverter: ProfileConverter,
    private val emailTokenValidator: EmailTokenValidator,
    private val accountOwnerValidator: AccountOwnerValidator,
    private val profilePolicyValidator: ProfilePolicyValidator,
    private val profileChangeInfoConverter: ProfileChangeInfoConverter
): ProfileCommandPreprocessor {
    //비밀번호 정책을 검사사한다.
    override fun validate(accountId: Long, request: ChangePasswordRequest): Mono<Unit> =
        //이메일 토큰을 decode한다.
        authTokenDecoder.decode(AuthTokenDto(token = request.emailToken))
            //decode해서 얻은 인증토큰을 validate한다. (인증토큰이 유효한 이메일 토큰인가)
            .flatMap { dto -> emailTokenValidator.validate(dto).map { dto.data } }
            //decode해서 얻은 email을 validate한다. (요청자가 해당email의 소유자인가)
            .flatMap { email -> accountOwnerValidator.validate(accountId, email) }

    //프로필 업데이트 정책을 검사한다.
    override fun validate(accountId: Long, request: UpdateProfileRequest): Mono<Unit> =
        //이메일 토큰을 decode한다.
        authTokenDecoder.decode(AuthTokenDto(token = request.emailToken))
            //decode해서 얻은 인증토큰을 validate한다. (인증토큰이 유효한 이메일 토큰인가)
            .flatMap { dto -> emailTokenValidator.validate(dto).map { dto.data } }
            //decode해서 얻은 email을 validate한다. (요청자가 해당email의 소유자인가)
            .flatMap { email -> accountOwnerValidator.validate(accountId, email).map { email } }
            //요청자 ID, 요청정보를 변경될 프로필에 필요한 부가정보로 convert 한다.
            .flatMap { profileChangeInfoConverter.convert(accountId, request) }
            //ProfileDto 구성에 필요한 부가정보와 요청정보를 ProfileDto로 convert한다.
            .flatMap { profileConverter.toDto(request, it.first, it.second) }
            //convert해서 얻은 프로필Dto를 validate한다. (프로필 정보가 유효한가)
            .flatMap { profileDto -> profilePolicyValidator.validate(profileDto) }

    override fun convert(accountId: Long, request: UpdateProfileRequest): Mono<ProfileDto> =
        //요청자 ID, 요청정보를 변경될 프로필에 필요한 부가정보로 convert 한다.
        profileChangeInfoConverter.convert(accountId, request)
            //ProfileDto 구성에 필요한 부가정보와 요청정보를 ProfileDto로 convert한다.
            .flatMap { profileConverter.toDto(request, it.first, it.second) }
}