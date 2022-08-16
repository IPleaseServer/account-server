package site.iplease.accountserver.domain.register.listener

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import site.iplease.accountserver.domain.register.data.event.StudentRegisteredEvent
import site.iplease.accountserver.domain.register.data.event.TeacherRegisteredEvent
import site.iplease.accountserver.infra.alarm.data.type.AlarmType
import site.iplease.accountserver.infra.alarm.service.PushAlarmService

@Component
class WelcomeRegisterMailListener(
    private val pushAlarmService: PushAlarmService
) {
    @EventListener(StudentRegisteredEvent::class)
    fun handleEvent(e: StudentRegisteredEvent) {
        val content = """
            ### 환영해요 ${e.name}님!
            안녕하세요 ${e.name}님! IPlease에 오신걸 환영해요!
            
            이제 **클릭 몇 번**으로 간편하게 IP를 할당받으실 수 있습니다.
            
            지금 바로 IP할당신청하러 가볼까요?
            
            _[서비스 바로가기](https://www.iplease.site)_
            
            _[프로모션 바로가기](https://iplease.notion.site/IPlease-7ff7b235306d4d0fa1d83c9d21d88f00)_
            
            가입자(학생) 정보
            > 이름: ${e.name}
            >
            > 이메일: ${e.email}
            >
            > 학번: ${e.studentNumber}
            >
            > 학과: ${e.department.displayName}
        """.trimIndent()
        pushAlarmService.publish(e.id, "[IPlease] 가입을 환영해요!", content, AlarmType.EMAIL).subscribe()
    }

    @EventListener(TeacherRegisteredEvent::class)
    fun handleEvent(e: TeacherRegisteredEvent) {
        val content = """
            ### 환영해요 ${e.name} 선생님!
            안녕하세요 ${e.name} 선생님! IPlease에 오신걸 환영해요!
            
            이제 **클릭 몇 번**으로 간편하게 IP를 관리하실 수 있습니다.
            
            학생들이 IP할당신청을 등록하면, 푸시알림으로 바로 알려드릴게요!
            
            _[서비스 바로가기](https://www.iplease.site)_
            
            _[프로모션 바로가기](https://iplease.notion.site/IPlease-7ff7b235306d4d0fa1d83c9d21d88f00)_
            
            가입자(교사) 정보
            > 이름: ${e.name}
            >
            > 이메일: ${e.email}
        """.trimIndent()
        pushAlarmService.publish(e.id, "[IPlease] 가입을 환영해요!", content, AlarmType.EMAIL).subscribe()
    }
}