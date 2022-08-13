package site.iplease.accountserver.domain.register.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.request.StudentRegisterRequest
import site.iplease.accountserver.domain.register.data.request.TeacherRegisterRequest

interface LegacyRegisterPreprocessor {
    fun valid(request: StudentRegisterRequest): Mono<Unit>//요청정보를 검사한다.
    fun valid(request: TeacherRegisterRequest): Mono<Unit>//요청정보를 검사한다.
    fun decodeAndConvert(request: StudentRegisterRequest): Mono<Pair<CommonRegisterDto, StudentAdditionalRegisterDto>>//요청정보를 해독한다.
    fun decodeAndConvert(request: TeacherRegisterRequest): Mono<Pair<CommonRegisterDto, TeacherAdditionalRegisterDto>>//요청정보를 해독한다.
}
