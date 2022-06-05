package site.iplease.accountserver.domain.profile.util

import com.google.protobuf.NullValue
import org.springframework.stereotype.Component
import site.iplease.accountserver.domain.profile.dto.ProfileDto
import site.iplease.accountserver.global.register.data.type.AccountType
import site.iplease.accountserver.global.register.data.type.DepartmentType
import site.iplease.accountserver.lib.*

@Component
class ProfileGrpcConverterImpl: ProfileGrpcConverter {
    override fun toGrpcResponse(dto: ProfileDto): GProfileResponse =
        GProfileResponse.newBuilder()
            .setStatus(GErrorType.SUCCESS)
            .setMessage("프로필 조회에 성공하였습니다!")
            .setValue(
                GProfileData.newBuilder()
                    .setType(toGAccountType(dto.type))
                    .setCommon(toGrpcCommonResponse(dto))
                    .setStudent(toGrpcStudentResponse(dto))
                    .setTeacher(toGrpcTeacherResponse(dto))
                    .build()
            ).build()

    override fun toGrpcCommonResponse(dto: ProfileDto): GCommonProfileResponse =
        GCommonProfileResponse.newBuilder()
            .setValue(
                GCommonProfileData.newBuilder()
                    .setAccountId(dto.accountId)
                    .setName(dto.name)
                    .setEmail(dto.email)
                    .setProfileImage(dto.profileImage.toString())
                    .build()
            ).build()

    override fun toGrpcStudentResponse(dto: ProfileDto): GStudentProfileResponse =
        GStudentProfileResponse.newBuilder().let {
            when (dto.type) {
                AccountType.TEACHER -> it.setNull(NullValue.NULL_VALUE)
                AccountType.STUDENT -> it.setValue(
                    GStudentProfileData.newBuilder()
                        .setStudentNumber(dto.studentNumber)
                        .setDepartment(toGDepartmentType(dto.department))
                ) } }.build()

    override fun toGrpcTeacherResponse(dto: ProfileDto): GTeacherProfileResponse =
        GTeacherProfileResponse.newBuilder().let {
            when (dto.type) {
                AccountType.STUDENT -> it.setNull(NullValue.NULL_VALUE)
                AccountType.TEACHER -> it.setValue(
                    GTeacherProfileData.newBuilder().build()
                ) } }.build()

    override fun error(status: GErrorType, msg: String): GProfileResponse =
        GProfileResponse.newBuilder()
            .setStatus(status)
            .setMessage(msg)
            .setNull(NullValue.NULL_VALUE)
            .build()

    private fun toGDepartmentType(department: DepartmentType): GDepartmentType =
        when(department) {
            DepartmentType.SMART_IOT -> GDepartmentType.SMART_IOT
            DepartmentType.SOFTWARE_DEVELOP -> GDepartmentType.SOFTWARE_DEVELOP
        }

    private fun toGAccountType(type: AccountType): GAccountType =
        when(type) {
            AccountType.STUDENT -> GAccountType.STUDENT
            AccountType.TEACHER -> GAccountType.TEACHER
        }
}