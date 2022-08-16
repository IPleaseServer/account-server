package site.iplease.accountserver.infra.alarm.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.infra.alarm.data.message.SendAlarmMessage
import site.iplease.accountserver.infra.alarm.data.type.AlarmType
import site.iplease.accountserver.infra.message.service.MessagePublishService
import site.iplease.accountserver.infra.message.type.MessageType

@Service
class PushAlarmServiceImpl(
    private val messagePublishService: MessagePublishService
): PushAlarmService {
    override fun publish(receiverId: Long, title: String, description: String, type: AlarmType): Mono<Unit> =
        Unit.toMono()
            .map { SendAlarmMessage(
                type = type,
                receiverId = receiverId,
                title = title,
                description = description
            ) }.flatMap { messagePublishService.publish(type = MessageType.SEND_ALARM, data = it) }
}