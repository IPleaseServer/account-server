package site.iplease.accountserver.domain.register.data.type

enum class PolicyType(val displayName: String) {
    REGISTER_EMAIL("회원가입 이메일"), REGISTER_STUDENT_NUMBER("회원가입 학번"),
    TEACHER_CODE("교사 인증코드")
}
