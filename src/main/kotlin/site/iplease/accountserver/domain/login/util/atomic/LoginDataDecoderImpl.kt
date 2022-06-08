package site.iplease.accountserver.domain.login.util.atomic

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.global.common.repository.AccountRepository
import site.iplease.accountserver.global.common.exception.PolicyViolationException
import site.iplease.accountserver.global.common.type.PolicyType

@Service
class LoginDataDecoderImpl(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
): LoginDataDecoder {
    override fun decode(email: String, password: String): Mono<Long> =
        accountRepository.existsByEmail(email)
            .flatMap {
                if(it) accountRepository.findByEmail(email)
                else Mono.error(PolicyViolationException(PolicyType.LOGIN_EMAIL, "존재하지않는 계정입니다."))
            }.flatMap {
                if(passwordEncoder.matches(password, it.encodedPassword)) it.id.toMono()
                else Mono.error(PolicyViolationException(PolicyType.LOGIN_PASSWORD, "잘못된 비밀번호입니다."))
            }
}