package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.global.common.entity.Account
import site.iplease.accountserver.global.common.type.AccountType
import site.iplease.accountserver.global.common.type.PermissionType

interface ProfileConverter {
    fun toEntity(profile: ProfileDto): Mono<Account>
    fun toDto(account: Account): Mono<ProfileDto>
    fun toDto(account: UpdateProfileRequest): Mono<ProfileDto>
    fun toEntity(
        account: AccountType,
        permission: PermissionType,
        encodedPassword: String,
        common: CommonRegisterDto,
        student: StudentAdditionalRegisterDto
    ): Mono<Account>

    fun toEntity(
        account: AccountType,
        permission: PermissionType,
        encodedPassword: String,
        common: CommonRegisterDto,
        student: TeacherAdditionalRegisterDto
    ): Mono<Account>
}