package site.iplease.accountserver.domain.profile.util

import com.google.protobuf.Int32Value
import com.google.protobuf.Int64Value
import com.google.protobuf.NullValue
import com.google.protobuf.StringValue
import org.springframework.stereotype.Component
import site.iplease.accountserver.domain.profile.dto.ProfileDto
import site.iplease.accountserver.global.register.data.type.AccountType
import site.iplease.accountserver.global.register.data.type.DepartmentType
import site.iplease.accountserver.lib.*

@Component
class ProfileGrpcConverterImpl: ProfileGrpcConverter {
    override fun toGrpcResponse(dto: ProfileDto): GProfileResponse =
        GProfileResponse.newBuilder()
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
                    .setAccountId(Int64Value.of(dto.accountId))
                    .setName(StringValue.of(dto.name))
                    .setEmail(StringValue.of(dto.email))
                    .setProfileImage(StringValue.of(dto.profileImage.toString()))
                    .build()
            ).build()

    override fun toGrpcStudentResponse(dto: ProfileDto): GStudentProfileResponse =
        GStudentProfileResponse.newBuilder().let {
            when (dto.type) {
                AccountType.TEACHER -> it.setNull(NullValue.NULL_VALUE)
                AccountType.STUDENT -> it.setValue(
                    GStudentProfileData.newBuilder()
                        .setStudentNumber(Int32Value.of(dto.studentNumber))
                        .setDepartment(toGDepartmentType(dto.department))
                ) } }.build()

    override fun toGrpcTeacherResponse(dto: ProfileDto): GTeacherProfileResponse =
        GTeacherProfileResponse.newBuilder().let {
            when (dto.type) {
                AccountType.STUDENT -> it.setNull(NullValue.NULL_VALUE)
                AccountType.TEACHER -> it.setValue(
                    GTeacherProfileData.newBuilder().build()
                ) } }.build()

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