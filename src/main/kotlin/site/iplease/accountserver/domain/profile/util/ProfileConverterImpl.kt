package site.iplease.accountserver.domain.profile.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.domain.profile.data.request.UpdateProfileRequest
import site.iplease.accountserver.domain.register.data.dto.CommonRegisterDto
import site.iplease.accountserver.domain.register.data.dto.StudentAdditionalRegisterDto
import site.iplease.accountserver.domain.register.data.dto.TeacherAdditionalRegisterDto
import site.iplease.accountserver.global.common.entity.Account
import site.iplease.accountserver.global.common.type.AccountType
import site.iplease.accountserver.global.common.type.PermissionType
import java.net.URI

@Component
class ProfileConverterImpl: ProfileConverter {
    override fun toEntity(profile: ProfileDto): Mono<Account> =
        Unit.toMono().map { Account(
            id = profile.accountId,
            type = profile.type,
            permission = profile.permission,
            name = profile.name,
            email = profile.email,
            encodedPassword = "",
            studentNumber = profile.studentNumber,
            department = profile.department
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
            studentNumber = student.studentNumber, department = student.department
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
        ) }

    override fun toDto(account: Account): Mono<ProfileDto> =
        Unit.toMono().map { ProfileDto(
            type = account.type,
            permission = account.permission,
            accountId = account.id,
            name = account.name,
            email = account.email,
            profileImage = URI.create("asd"), //TODO Account 엔티티에 이미지정보 추가하고 수정
            studentNumber = account.studentNumber,
            department = account.department
        ) }

    override fun toDto(account: UpdateProfileRequest): Mono<ProfileDto> {
        TODO("Not yet implemented")
    }
}