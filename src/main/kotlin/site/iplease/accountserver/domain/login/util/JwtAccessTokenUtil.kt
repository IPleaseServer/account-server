package site.iplease.accountserver.domain.login.util

import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.domain.login.config.LoginProperties
import site.iplease.accountserver.domain.register.data.entity.Account
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class JwtAccessTokenUtil(
    private val loginProperties: LoginProperties
): AccessTokenUtil {
    private val jwtProperties = lazy { loginProperties.accessProperties }

    override fun generate(account: Account): Mono<String> =
        LocalDateTime.now().toMono().map {
            Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.value.issuer)
                .setIssuedAt(Timestamp.valueOf(it))
                .setExpiration(Timestamp.valueOf(it.plusSeconds(jwtProperties.value.expireSeconds)))
                .claim("id", account.id)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.value.secret)
                .compact()
        }

    override fun decode(token: String): Mono<Long> =
        Jwts.parser()
            .setSigningKey(jwtProperties.value.secret)
            .parseClaimsJws(token).body.toMono()
            .map { it.get("id", Long::class.java) }
}