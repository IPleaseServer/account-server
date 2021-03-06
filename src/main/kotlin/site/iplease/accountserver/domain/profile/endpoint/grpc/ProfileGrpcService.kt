package site.iplease.accountserver.domain.profile.endpoint.grpc

import com.google.protobuf.BoolValue
import com.google.protobuf.Int64Value
import com.google.protobuf.StringValue
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import site.iplease.accountserver.domain.profile.service.ProfileService
import site.iplease.accountserver.domain.profile.util.ProfileGrpcConverter
import site.iplease.accountserver.global.common.exception.PolicyViolationException
import site.iplease.accountserver.global.common.exception.UnknownAccountException
import site.iplease.accountserver.lib.GErrorType
import site.iplease.accountserver.lib.GProfileResponse
import site.iplease.accountserver.lib.ReactorProfileServiceGrpc.ProfileServiceImplBase

@Component
class ProfileGrpcService(
    val profileService: ProfileService,
    val profileGrpcConverter: ProfileGrpcConverter
): ProfileServiceImplBase() {
    private val logger = LoggerFactory.getLogger(this::class.java)

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

    override fun existProfileByAccountToken(request: Mono<StringValue>): Mono<BoolValue> =
        request.map { it.value }
            .flatMap { profileService.existProfileByAccessToken(it) }
            .map { BoolValue.of(it) }

    private fun Mono<GProfileResponse>.handleException(): Mono<GProfileResponse> =
        onErrorReturn(MalformedJwtException::class.java, GErrorType.CLIENT_ERROR, "????????? ????????? Jwt???????????????!")
            .onErrorReturn(SignatureException::class.java, GErrorType.CLIENT_ERROR, "????????? ????????? Jwt???????????????!")
            .onErrorReturn(ExpiredJwtException::class.java, GErrorType.CLIENT_ERROR, "?????? ????????? Jwt???????????????!")
            .onErrorReturn(UnsupportedJwtException::class.java, GErrorType.CLIENT_ERROR, "???????????? ?????? Jwt???????????????!")
            .onErrorReturn(IllegalArgumentException::class.java, GErrorType.CLIENT_ERROR, "????????? Jwt???????????????!")
            .onErrorReturn(PolicyViolationException::class.java, GErrorType.CLIENT_ERROR, "????????? ?????????????????????!")
            .onErrorReturn(UnknownAccountException::class.java, GErrorType.CLIENT_ERROR, "???????????? ????????? ????????? ?????? ??? ????????????!")
            .doOnError{ logger.warn("????????? ?????????????????????! - ${it.message}") }
            .onErrorReturn(profileGrpcConverter.error(GErrorType.UNKNOWN_ERROR, "??? ??? ?????? ????????? ?????????????????????!"))

    private fun <E: Throwable> Mono<GProfileResponse>.onErrorReturn(java: Class<E>, status: GErrorType, msg: String) =
        onErrorReturn(java, profileGrpcConverter.error(status, msg))
}

