package site.iplease.accountserver.domain.auth.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.accountserver.infra.teacher_code.exception.TeacherAuthorizerDuplicatedKeyException
import site.iplease.accountserver.infra.teacher_code.service.TeacherAuthorizerService

@Service
class DummyTeacherAuthorizerService: TeacherAuthorizerService {
    override fun sendData(key: String, value: String): Mono<String> =
        if(listOf("key", "veldanava", "electron").none { key.startsWith(it) }) Mono.defer {
            println("@Dummy 학생 인증기에 새로운 정보가 등록되었습니다! $key:$value")
            return@defer value.toMono() }
        else Mono.error(TeacherAuthorizerDuplicatedKeyException(key, "@Dummy 인증키가 중복되었습니다!"))
}