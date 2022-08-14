package site.iplease.accountserver.domain.register.util

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.config.ProfileImageProperties
import site.iplease.accountserver.domain.register.data.dto.StudentRegisterValidationDto
import site.iplease.accountserver.domain.register.data.dto.StudentRegistrationDto
import site.iplease.accountserver.domain.register.data.dto.TeacherRegisterValidationDto
import site.iplease.accountserver.domain.register.data.dto.TeacherRegistrationDto
import site.iplease.accountserver.global.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder

@Component
class RegisterProcessorImpl(
    private val authTokenDecoder: AuthTokenDecoder,
    private val passwordEncoder: PasswordEncoder,
    private val profileImageProperties: ProfileImageProperties
): RegisterProcessor {
    override fun process(validatedDto: StudentRegisterValidationDto): Mono<StudentRegistrationDto> =
        authTokenDecoder.decode(AuthTokenDto(validatedDto.emailToken)).map { it.data }
            .flatMap{ email -> encodePassword(validatedDto.password).map{ email to it } }
            .map { StudentRegistrationDto(
                name = validatedDto.name,
                email = it.first,
                encodedPassword =  it.second,
                studentNumber = validatedDto.studentNumber,
                department = validatedDto.department,
                profileImageUrl = profileImageProperties.defaultUrl
            ) }

    override fun process(validatedDto: TeacherRegisterValidationDto): Mono<TeacherRegistrationDto> =
        authTokenDecoder.decode(AuthTokenDto(validatedDto.emailToken)).map { it.data }
            .flatMap{ email -> encodePassword(validatedDto.password).map{ email to it } }
            .map { TeacherRegistrationDto(
                name = validatedDto.name,
                email = it.first,
                encodedPassword =  it.second,
                profileImageUrl = profileImageProperties.defaultUrl
            ) }

    private fun encodePassword(password: String): Mono<String> =
        passwordEncoder.encode(password).toMono()
}