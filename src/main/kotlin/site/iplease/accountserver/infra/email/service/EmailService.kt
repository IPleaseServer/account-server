package site.iplease.accountserver.infra.email.service

import reactor.core.publisher.Mono

interface EmailService {
    fun sendEmail(to: String, title: String, template: String, context: Map<String, String>): Mono<Unit>

}
