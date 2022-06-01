package site.iplease.accountserver.domain.auth.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.infra.email.service.EmailService

@Service //TODO 실제 구현체 작성 후 제거 예정
class DummyEmailService: EmailService {
    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun sendEmail(to: String, title: String, template: String, context: Map<String, String>): Mono<Unit> {
        logger.info("이메일을 전송합니다!")
        logger.info("수신자: $to")
        logger.info("제목: $title")
        logger.info("본문 템플릿: $template")
        logger.info("본문 컨텍스트: $context")
        return Unit.toMono()
    }
}