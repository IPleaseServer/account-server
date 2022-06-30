package site.iplease.accountserver.domain.profile.util

import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.lib.*

interface ProfileGrpcConverter {
    fun toGrpcResponse(dto: ProfileDto): GProfileResponse
    fun toGrpcCommonResponse(dto: ProfileDto): GCommonProfileResponse
    fun toGrpcStudentResponse(dto: ProfileDto): GStudentProfileResponse
    fun toGrpcTeacherResponse(dto: ProfileDto): GTeacherProfileResponse
    fun error(status: GErrorType, msg: String): GProfileResponse
}
