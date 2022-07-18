package site.iplease.accountserver.infra.teacher_code.service

import reactor.core.publisher.Mono

interface TeacherAuthorizerService {
    //TODO 실서비스 객체 구현예정
    //이미 존재하는 key일경우 Exception 발생
    fun sendData(key: String, value: String): Mono<String>
}
