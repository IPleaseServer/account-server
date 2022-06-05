package site.iplease.accountserver.domain.register.exception

class WrongEmailTokenException(reason: String): RuntimeException("잘못된 이메일 토큰입니다! - $reason")