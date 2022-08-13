package site.iplease.accountserver.domain.register.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.dto.StudentRegistrationDto

interface RegisterService {
    fun registerStudent(dto: StudentRegistrationDto): Mono<StudentRegistrationDto>

}
