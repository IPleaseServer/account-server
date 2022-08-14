package site.iplease.accountserver.domain.register.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentDto
import site.iplease.accountserver.domain.register.data.dto.StudentRegistrationDto
import site.iplease.accountserver.domain.register.data.dto.TeacherDto
import site.iplease.accountserver.domain.register.data.dto.TeacherRegistrationDto

interface RegisterService {
    fun registerStudent(dto: StudentRegistrationDto): Mono<StudentDto>
    fun registerTeacher(dto: TeacherRegistrationDto): Mono<TeacherDto>
}
