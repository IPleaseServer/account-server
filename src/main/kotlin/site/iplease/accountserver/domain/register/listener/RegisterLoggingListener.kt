package site.iplease.accountserver.domain.register.listener

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import site.iplease.accountserver.domain.register.data.event.StudentRegisteredEvent
import site.iplease.accountserver.domain.register.data.event.TeacherRegisteredEvent
import kotlin.random.Random

@Component
class RegisterLoggingListener {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    @EventListener(StudentRegisteredEvent::class)
    fun handleEvent(e: StudentRegisteredEvent) {
        val id = Random.nextLong()
        val 학년 = e.studentNumber/1000; val 반 = e.studentNumber/100%10; val 번호 = e.studentNumber%100
        logger.info("[$id] 새로운 학생이 회원가입했어요!")
        logger.info("[$id] 학생정보: ${e.department.displayName} ${학년}학년 ${반}반 ${번호}번 ${e.name}")
        logger.info("[$id] 식별자: ${e.id}")
        logger.info("[$id] 이메일: ${e.email}")
    }

    @EventListener(TeacherRegisteredEvent::class)
    fun handleEvent(e: TeacherRegisteredEvent) {
        val id = Random.nextLong()
        logger.info("[$id] 새로운 선생님이 회원가입했어요!")
        logger.info("[$id] 교사정보: ${e.name} 선생님")
        logger.info("[$id] 식별자: ${e.id}")
        logger.info("[$id] 이메일: ${e.email}")
    }
}