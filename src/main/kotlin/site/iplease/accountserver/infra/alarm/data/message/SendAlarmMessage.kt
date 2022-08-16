package site.iplease.accountserver.infra.alarm.data.message

import site.iplease.accountserver.infra.alarm.data.type.AlarmType

data class SendAlarmMessage (
    val type: AlarmType,
    val receiverId: Long,
    val title: String,
    val description: String
)