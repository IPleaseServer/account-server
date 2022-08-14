package site.iplease.accountserver.domain.register.util

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto
import site.iplease.accountserver.domain.register.data.dto.StudentRegistrationDto
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder

@Component
class RegisterProcessorImpl(
    private val authTokenDecoder: AuthTokenDecoder,
    private val passwordEncoder: PasswordEncoder
): RegisterProcessor {
    override fun process(validatedDto: StudentRegisterValidationDto): Mono<StudentRegistrationDto> =
        authTokenDecoder.decode(AuthTokenDto(validatedDto.emailToken)).map { it.data }
            .flatMap{ email -> encodePassword(validatedDto.password).map{ email to it } }
            .map{ StudentRegistrationDto(
                name = validatedDto.name,
                email = it.first,
                encodedPassword =  it.second,
                studentNumber = validatedDto.studentNumber,
                department = validatedDto.department,
            ) }

    private fun encodePassword(password: String): Mono<String> =
        passwordEncoder.encode(password).toMono()
}