package site.iplease.accountserver.domain.profile.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.config.ProfileImageProperties
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.domain.profile.data.response.CommonProfileResponse
import site.iplease.accountserver.domain.profile.data.response.ProfileResponse
import site.iplease.accountserver.domain.profile.data.response.StudentProfileResponse
import site.iplease.accountserver.domain.profile.data.response.TeacherProfileResponse
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.global.common.entity.Account
import site.iplease.accountserver.global.common.type.AccountType
import site.iplease.accountserver.global.common.type.PermissionType
import java.net.URI

@Component
class ProfileConverterImpl(
    private val profileImageProperties: ProfileImageProperties
): ProfileConverter {
    override fun toEntity(profile: ProfileDto): Mono<Account> =
        Unit.toMono().map { Account(
            id = profile.accountId,
            type = profile.type,
            permission = profile.permission,
            name = profile.name,
            email = profile.email,
            encodedPassword = "",
            studentNumber = profile.studentNumber,
            department = profile.department,
            profileImageUrl = profile.profileImage.toString()
        ) }

    override fun toEntity(
        account: AccountType,
        permission: PermissionType,
        encodedPassword: String,
        common: CommonRegisterDto,
        student: StudentAdditionalRegisterDto
    ): Mono<Account> =
        Unit.toMono().map { Account(//회원정보를 구성한다.
            type = AccountType.STUDENT, permission = PermissionType.USER,
            name = common.name, email = common.email, encodedPassword = encodedPassword,
            studentNumber = student.studentNumber, department = student.department,
            profileImageUrl = profileImageProperties.defaultUrl
        ) }

    override fun toEntity(
        account: AccountType,
        permission: PermissionType,
        encodedPassword: String,
        common: CommonRegisterDto,
        student: TeacherAdditionalRegisterDto
    ): Mono<Account> =
        Unit.toMono().map { Account(//회원정보를 구성한다.
            type = AccountType.TEACHER, permission = PermissionType.OPERATOR,
            name = common.name, email = common.email, encodedPassword = encodedPassword,
            profileImageUrl = profileImageProperties.defaultUrl
        ) }

    override fun toDto(account: Account): Mono<ProfileDto> =
        Unit.toMono().map { ProfileDto(
            type = account.type,
            permission = account.permission,
            accountId = account.id,
            name = account.name,
            email = account.email,
            profileImage = URI.create(account.profileImageUrl),
            studentNumber = account.studentNumber,
            department = account.department
        ) }

    override fun toDto(request: UpdateProfileRequest, account: Account, email: String): Mono<ProfileDto> =
        Unit.toMono().map { ProfileDto( //이메일, 기존 계정 정보, 프로필 변경 요청정보를 통해 변경될 프로필을 생성한다.
            type = request.type,
            permission = request.permission,
            accountId = account.id,
            name = request.name ?: account.name,
            email = email,
            profileImage = request.profileImage ?: URI.create(account.profileImageUrl),
            studentNumber = request.studentNumber ?: account.studentNumber,
            department = request.department ?: account.department,
        ) }

    override fun toResponse(dto: ProfileDto): Mono<ProfileResponse> =
        Unit.toMono().map {
            ProfileResponse(
                type = dto.type,
                common = CommonProfileResponse(
                    accountId = dto.accountId,
                    permission = dto.permission,
                    name = dto.name,
                    email = dto.email,
                    profileImage = dto.profileImage.toString()
                ),
                teacher = if (dto.type == AccountType.TEACHER) TeacherProfileResponse() else null,
                student = if (dto.type == AccountType.STUDENT) StudentProfileResponse(
                    studentNumber = dto.studentNumber,
                    department = dto.department
                ) else null
            )
        }
}