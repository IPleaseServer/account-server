package site.iplease.accountserver.domain.register.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.auth.data.dto.AuthDto
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.domain.auth.data.type.AuthType
import site.iplease.accountserver.domain.auth.util.atomic.AuthTokenDecoder
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.request.StudentRegisterRequest
import site.iplease.accountserver.domain.register.data.request.TeacherRegisterRequest
import site.iplease.accountserver.domain.register.data.type.DepartmentType
import site.iplease.accountserver.domain.register.exception.WrongDepartmentOrStudentNumberException
import site.iplease.accountserver.domain.register.exception.WrongEmailTokenException

@Component
class RegisterPreprocessorImpl(
    private val authTokenDecoder: AuthTokenDecoder
): RegisterPreprocessor {
    override fun valid(request: StudentRegisterRequest): Mono<Unit> =
        validEmailToken(request.emailToken) //이메일 토큰값을 검증한다.
            .flatMap { validDepartmentAndStudentNumber(request.department, request.studentNumber) } //학번과 학과를 검증한다.

    override fun valid(request: TeacherRegisterRequest): Mono<Unit> =
        validEmailToken(request.emailToken) //이메일 토큰값을 검증한다.

    //학번과 학과를 검증한다.
    private fun validDepartmentAndStudentNumber(department: DepartmentType, studentNumber: Int): Mono<Unit> {
        fun getClass(studentNumber: Int) = studentNumber % 1000 / 100
        return department.toMono()
            .map { it.classes.contains(getClass(studentNumber)) }
            .flatMap {
                if(it) Unit.toMono()
                else Mono.error(WrongDepartmentOrStudentNumberException("${getClass(studentNumber)}반은 $department 학과의 반이 아닙니다!"))
            }
    }

    //이메일 토큰을 검증한다.
    private fun validEmailToken(emailToken: String): Mono<Unit> =
        decodeAuthToken(emailToken)
            .flatMap {
                if(it.type == AuthType.EMAIL) Unit.toMono()
                else Mono.error(WrongEmailTokenException("해당 토큰은 ${it.type}토큰입니다!"))
            }

    override fun decode(request: StudentRegisterRequest): Mono<Pair<CommonRegisterDto, StudentAdditionalRegisterDto>> =
        decodeAuthToken(request.emailToken) //이메일토큰을 해독한다.
            .map { CommonRegisterDto(name = request.name, email = it.data, password = request.password) } //해독한 값들을 통해 CommonRegisterDto를 구성한다.
            .map { it to StudentAdditionalRegisterDto(studentNumber = request.studentNumber, department = request.department) } //해독한 값들을 통해 TeacherAdditionalRegisterDto를 구성하고 반환한다.

    override fun decode(request: TeacherRegisterRequest): Mono<Pair<CommonRegisterDto, TeacherAdditionalRegisterDto>> =
        decodeAuthToken(request.emailToken) //이메일토큰을 해독한다.
            .map { CommonRegisterDto(name = request.name, email = it.data, password = request.password) } //해독한 값들을 통해 CommonRegisterDto를 구성한다.
            .map { it to TeacherAdditionalRegisterDto(teacherCode = request.teacherCode) } //해독한 값들을 통해 TeacherAdditionalRegisterDto를 구성하고 반환한다.

    //이메일 토큰을 해독한다.
    private fun decodeAuthToken(emailToken: String): Mono<AuthDto> =
        emailToken.toMono()
            .map { AuthTokenDto(it) }
            .flatMap { authTokenDecoder.decode(it) }
}