package site.iplease.accountserver.domain.profile.endpoint.grpc

import com.google.protobuf.Int64Value
import com.google.protobuf.StringValue
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.service.ProfileService
import site.iplease.accountserver.domain.profile.util.ProfileGrpcConverter
import site.iplease.accountserver.global.common.exception.PolicyViolationException
import site.iplease.accountserver.lib.GErrorType
import site.iplease.accountserver.lib.GProfileResponse
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
            .handleException()

    override fun getProfileByAccountId(request: Mono<Int64Value>): Mono<GProfileResponse> =
        request.map { it.value }
            .flatMap { profileService.getProfileByAccountId(it) }
            .map { profileGrpcConverter.toGrpcResponse(it) }
            .handleException()

    private fun Mono<GProfileResponse>.handleException(): Mono<GProfileResponse> =
        onErrorReturn(MalformedJwtException::class.java, GErrorType.CLIENT_ERROR, "잘못된 형식의 Jwt토큰입니다!")
            .onErrorReturn(SignatureException::class.java, GErrorType.CLIENT_ERROR, "잘못된 형식의 Jwt토큰입니다!")
            .onErrorReturn(ExpiredJwtException::class.java, GErrorType.CLIENT_ERROR, "이미 만료된 Jwt토큰입니다!")
            .onErrorReturn(UnsupportedJwtException::class.java, GErrorType.CLIENT_ERROR, "지원하지 않는 Jwt토큰입니다!")
            .onErrorReturn(IllegalArgumentException::class.java, GErrorType.CLIENT_ERROR, "잘못된 Jwt토큰입니다!")
            .onErrorReturn(PolicyViolationException::class.java, GErrorType.CLIENT_ERROR, "정책을 위반하였습니다!")
            .onErrorReturn(profileGrpcConverter.error(GErrorType.UNKNOWN_ERROR, "알 수 없는 오류가 발생하였습니다!"))

    private fun <E: Throwable> Mono<GProfileResponse>.onErrorReturn(java: Class<E>, status: GErrorType, msg: String) =
        onErrorReturn(java, profileGrpcConverter.error(status, msg))
}

