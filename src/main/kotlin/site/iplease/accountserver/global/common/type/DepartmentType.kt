package site.iplease.accountserver.global.common.type

enum class DepartmentType(
    vararg clazz: Int
) {
    SMART_IOT(3, 4), SOFTWARE_DEVELOP(1, 2), UNKNOWN;

    val classes = clazz.toSet()
}