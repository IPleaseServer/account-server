package site.iplease.accountserver.domain.register.exception

class WrongDepartmentOrStudentNumberException(s: String): RuntimeException("잘못된 학번 또는 학과입니다! - $s")