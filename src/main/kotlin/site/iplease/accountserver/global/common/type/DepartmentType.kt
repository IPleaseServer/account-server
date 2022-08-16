package site.iplease.accountserver.global.common.type

enum class DepartmentType(
    vararg clazz: Int,
    val displayName: String
) {
    SMART_IOT(3, 4, displayName = "스마트 IOT과"),
    SOFTWARE_DEVELOP(1, 2, displayName = "소프트웨어 개발과");

    val classes = clazz.toSet()
}