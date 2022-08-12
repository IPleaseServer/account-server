package site.iplease.accountserver.infra.email.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.infra.email.config.EmailProperty
import javax.mail.internet.MimeMessage

@Service
class JmsEmailService(
    private val javaMailSender: JavaMailSender,
    private val springTemplateEngine: ISpringWebFluxTemplateEngine,
    private val emailProperty: EmailProperty
): EmailService {
    override fun sendEmail(to: String, title: String, template: String, context: Map<String, String>): Mono<Unit> {
        return javaMailSender.createMimeMessage().apply {
            addRecipients(MimeMessage.RecipientType.TO, to)
            setFrom(emailProperty.from)
            subject = title
            Context()
                .also { it.setVariables(context) }
                .let { springTemplateEngine.process(template, it) }
                .let { setText(it, Charsets.UTF_8.displayName(), "html") }
        }.let { javaMailSender.send(it) }.toMono()
    }
}