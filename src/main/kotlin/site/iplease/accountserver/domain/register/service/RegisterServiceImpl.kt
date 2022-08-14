package site.iplease.accountserver.domain.register.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.StudentDto
import site.iplease.accountserver.domain.register.data.dto.StudentRegistrationDto
import site.iplease.accountserver.domain.register.data.dto.TeacherDto
import site.iplease.accountserver.domain.register.data.dto.TeacherRegistrationDto
import site.iplease.accountserver.domain.register.util.RegisterConverter
import site.iplease.accountserver.global.common.repository.AccountRepository

@Service
class RegisterServiceImpl(
    private val registerConverter: RegisterConverter,
    private val accountRepository: AccountRepository
): RegisterService {
    override fun registerStudent(dto: StudentRegistrationDto): Mono<StudentDto> =
        registerConverter.toEntity(dto)
            .map { it.copy(id=0) }
            .flatMap { newEntity -> accountRepository.save(newEntity) }
            .flatMap { savedEntity -> registerConverter.toStudentDto(savedEntity) }

    override fun registerTeacher(dto: TeacherRegistrationDto): Mono<TeacherDto> =
        registerConverter.toEntity(dto)
            .map { it.copy(id=0) }
            .flatMap { newEntity -> accountRepository.save(newEntity) }
            .flatMap { savedEntity -> registerConverter.toTeacherDto(savedEntity) }
}