package site.iplease.accountserver.domain.profile.endpoint.grpc

import com.google.protobuf.Int64Value
import com.google.protobuf.StringValue
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.service.ProfileService
import site.iplease.accountserver.domain.profile.util.ProfileGrpcConverter
import site.iplease.accountserver.lib.*
import site.iplease.accountserver.lib.ReactorProfileServiceGrpc.ProfileServiceImplBase

@Component
class ProfileGrpcService(
    val profileService: ProfileService,
    val profileGrpcConverter: ProfileGrpcConverter
): ProfileServiceImplBase() {
    override fun getProfileByAccessToken(request: Mono<StringValue>): Mono<GProfileResponse> =
        request.map { it.value }
            .flatMap { profileService.getProfileByAccessToken(it) }
            .map { profileGrpcConverter.toGrpcResponse(it) }

    override fun getProfileByAccountId(request: Mono<Int64Value>): Mono<GProfileResponse> {
        return super.getProfileByAccountId(request)
    }
}
