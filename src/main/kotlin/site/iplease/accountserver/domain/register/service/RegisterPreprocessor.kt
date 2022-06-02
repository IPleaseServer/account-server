package site.iplease.accountserver.domain.register.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.request.StudentRegisterRequest
import site.iplease.accountserver.domain.register.data.request.TeacherRegisterRequest

interface RegisterPreprocessor {
    fun valid(request: StudentRegisterRequest): Mono<Unit>//요청정보를 검사한다.
    fun valid(request: TeacherRegisterRequest): Mono<Unit>//요청정보를 검사한다.
    fun decode(request: StudentRegisterRequest): Mono<Pair<CommonRegisterDto, StudentAdditionalRegisterDto>>//요청정보를 해독한다.
    fun decode(request: TeacherRegisterRequest): Mono<Pair<CommonRegisterDto, TeacherAdditionalRegisterDto>>//요청정보를 해독한다.
}
