package site.iplease.accountserver.domain.profile.util

import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.data.dto.ProfileDto

interface ProfilePolicyValidator {
    fun validate(profileDto: ProfileDto): Mono<Unit>
}
