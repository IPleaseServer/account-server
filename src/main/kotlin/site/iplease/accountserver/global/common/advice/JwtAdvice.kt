package site.iplease.accountserver.global.common.advice

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.kotlin.core.publisher.toMono

@RestControllerAdvice
class JwtAdvice {
    @ExceptionHandler(MalformedJwtException ::class)
    fun handle(e: MalformedJwtException) = ResponseEntity.badRequest().body("잘못된 형식의 Jwt토큰입니다!").toMono()

    @ExceptionHandler(SignatureException::class)
    fun handle(e: SignatureException) = ResponseEntity.badRequest().body("잘못된 형식의 Jwt토큰입니다!").toMono()

    @ExceptionHandler(ExpiredJwtException::class)
    fun handle(e: ExpiredJwtException) = ResponseEntity.badRequest().body("이미 만료된 Jwt토큰입니다!").toMono()

    @ExceptionHandler(UnsupportedJwtException::class)
    fun handle(e: UnsupportedJwtException) = ResponseEntity.badRequest().body("지원하지 않는 Jwt토큰입니다!").toMono()

    @ExceptionHandler(IllegalArgumentException ::class)
    fun handle(e: IllegalArgumentException ) = ResponseEntity.badRequest().body("잘못된 Jwt토큰입니다!").toMono()
}