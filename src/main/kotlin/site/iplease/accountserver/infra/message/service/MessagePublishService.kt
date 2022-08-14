package site.iplease.accountserver.infra.message.service

import reactor.core.publisher.Mono
import site.iplease.accountserver.infra.message.type.MessageType

interface MessagePublishService {
    fun publish(type: MessageType, data: Any): Mono<Unit>
}
