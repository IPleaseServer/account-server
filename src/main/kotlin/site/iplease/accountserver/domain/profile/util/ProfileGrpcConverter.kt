package site.iplease.accountserver.domain.profile.util

import site.iplease.accountserver.domain.profile.dto.ProfileDto
import site.iplease.accountserver.lib.GCommonProfileResponse
import site.iplease.accountserver.lib.GProfileResponse
import site.iplease.accountserver.lib.GStudentProfileResponse
import site.iplease.accountserver.lib.GTeacherProfileResponse

interface ProfileGrpcConverter {
    fun toGrpcResponse(dto: ProfileDto): GProfileResponse
    fun toGrpcCommonResponse(dto: ProfileDto): GCommonProfileResponse
    fun toGrpcStudentResponse(dto: ProfileDto): GStudentProfileResponse
    fun toGrpcTeacherResponse(dto: ProfileDto): GTeacherProfileResponse
}
