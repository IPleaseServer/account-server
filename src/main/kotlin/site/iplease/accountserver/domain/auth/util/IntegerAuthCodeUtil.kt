package site.iplease.accountserver.domain.auth.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.auth.repository.AuthCodeRepository

@Component
class IntegerAuthCodeUtil(
    private val authCodeRepository: AuthCodeRepository
): AuthCodeUtil {
    override fun generate(): Mono<String> = generate(generateAuthCode())
    private fun generate(codeCandidate: String): Mono<String> = authCodeRepository.exist(codeCandidate)
        .flatMap {
            if(it) generate(generateAuthCode())
            else codeCandidate.toMono()
        }
    private fun generateAuthCode() = (0..999999).random().let { String.format("%06d", it) }

    override fun valid(input: String): Mono<Boolean> =
        input.matches(Regex("[0-9]{6}")).toMono()
}