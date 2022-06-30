package site.iplease.accountserver.domain.profile.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto
import site.iplease.accountserver.global.common.entity.Account
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
}