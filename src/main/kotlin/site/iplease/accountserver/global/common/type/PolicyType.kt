package site.iplease.accountserver.global.common.type

enum class PolicyType(val displayName: String) {
    TEACHER_CODE("교사 인증코드"),
    REGISTER_EMAIL("회원가입 이메일"), REGISTER_STUDENT_NUMBER("회원가입 학번"),
    LOGIN_EMAIL("로그인 이메일"), LOGIN_PASSWORD("로그인 비밀번호"),
    LOGIN_REFRESH_TOKEN("로그인 재발급 토큰"),
    DEPARTMENT("학과"), NAME("이름"), PROFILE_IMAGE("프로필 이미지"), STUDENT_NUMBER("학번"),
}
