package site.iplease.accountserver.domain.auth.util

import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.auth.config.AuthProperties
import site.iplease.accountserver.domain.auth.data.dto.AuthDto
import site.iplease.accountserver.domain.auth.data.dto.AuthTokenDto
import site.iplease.accountserver.domain.auth.data.type.AuthType
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class JwtAuthTokenUtil(
    val authProperties: AuthProperties
): AuthTokenUtil {
    val jwtProperties = lazy { authProperties.jwtProperties }

    override fun encode(dto: AuthDto): Mono<AuthTokenDto> =
        LocalDateTime.now().toMono().map {
            Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.value.issuer)
                .setIssuedAt(Timestamp.valueOf(it))
                .setExpiration(Timestamp.valueOf(it.plusSeconds(jwtProperties.value.expireSecond)))
                .claim("type", dto.type)
                .claim("data", dto.data)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.value.secret)
                .compact()
        }.map { AuthTokenDto(token = it) }

    override fun decode(dto: AuthTokenDto): Mono<AuthDto> =
        Jwts.parser()
            .setSigningKey(jwtProperties.value.secret)
            .parseClaimsJws(dto.token).body.toMono().map {
                it.get("type", AuthType::class.java) to
                it.get("data", String::class.java)
            }.map { AuthDto(it.first, it.second) }
}