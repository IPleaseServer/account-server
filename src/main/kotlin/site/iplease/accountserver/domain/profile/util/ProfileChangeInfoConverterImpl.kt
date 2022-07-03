package site.iplease.accountserver.domain.profile.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder
import site.iplease.accountserver.global.common.entity.Account
import site.iplease.accountserver.global.common.repository.AccountRepository

@Component
class ProfileChangeInfoConverterImpl(
    private val accountRepository: AccountRepository,
    private val authTokenDecoder: AuthTokenDecoder
): ProfileChangeInfoConverter {
    override fun convert(accountId: Long, request: UpdateProfileRequest): Mono<Pair<Account, String>> =
        accountRepository.findById(accountId).flatMap { account ->
            //신규 이메일이 존재할 경우, 신규 이메일과 계정엔티티를 매핑한다.
            if (request.newEmailToken != null && request.newEmailToken.isNotEmpty())
                authTokenDecoder.decode(AuthTokenDto(request.newEmailToken)).map { account to it.data }
            //신규 이메일이 존재하지 않을 경우, 기존 이메일과 계정 엔티티를 매핑한다.
            else (account to account.email).toMono()
        }
}