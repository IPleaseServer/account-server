package site.iplease.accountserver.domain.register.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto

interface RegisterService {
    //회원가입 정책을 검사한다.
    //회원정보를 구성한다.
    //구성한 회원정보를 저장한다.
    fun registerStudent(common: CommonRegisterDto, student: StudentAdditionalRegisterDto): Mono<Long>
    //학생 회원가입 정책을 검사한다.
    //회원정보를 구성한다.
    //구성한 회원정보를 저장한다.
    fun registerTeacher(common: CommonRegisterDto, teacher: TeacherAdditionalRegisterDto): Mono<Long>
}
