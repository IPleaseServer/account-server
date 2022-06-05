package site.iplease.accountserver.infra.teacher_code.exception

class TeacherAuthorizerDuplicatedKeyException(key: String, message: String): RuntimeException("이미 교사인증기에 해당키로 매핑된 정보가 존재합니다! $message - $key")
