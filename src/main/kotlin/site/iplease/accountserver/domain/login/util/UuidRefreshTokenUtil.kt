package site.iplease.accountserver.domain.login.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.login.repository.RefreshTokenRepository
import site.iplease.accountserver.domain.register.data.entity.Account
import site.iplease.accountserver.global.common.exception.PolicyViolationException
import site.iplease.accountserver.global.common.type.PolicyType
import java.util.*

@Component
class UuidRefreshTokenUtil(
    private val refreshTokenRepository: RefreshTokenRepository
): RefreshTokenUtil {
    override fun decode(refreshToken: String): Mono<Long> =
        refreshTokenRepository.exist(refreshToken)
            .flatMap {
                if(it) refreshTokenRepository.select(refreshToken)
                else Mono.error(PolicyViolationException(PolicyType.LOGIN_REFRESH_TOKEN, "존재하지 않는 재발급토큰입니다. - $refreshToken"))
            }.map { it.accountId }

    override fun generate(account: Account): Mono<String> = generateRefreshToken().toMono()
    private fun generateRefreshToken() = UUID.randomUUID().toString()
}