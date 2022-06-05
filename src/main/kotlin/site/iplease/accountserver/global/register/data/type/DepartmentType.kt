package site.iplease.accountserver.global.register.data.type

enum class DepartmentType(
    vararg clazz: Int
) {
    SMART_IOT(3, 4), SOFTWARE_DEVELOP(1, 2);

    val classes = clazz.toSet()
}
