package site.iplease.accountserver.global.common.exception

class UnknownAccountException(s: String): RuntimeException("계정을 찾을 수 없습니다! - $s")
