package site.iplease.accountserver.global.common.config

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats
import com.linecorp.armeria.server.docs.DocService
import com.linecorp.armeria.server.grpc.GrpcService
import com.linecorp.armeria.server.logging.LoggingService
import com.linecorp.armeria.spring.ArmeriaServerConfigurator
import io.grpc.protobuf.services.ProtoReflectionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import site.iplease.accountserver.domain.profile.endpoint.grpc.ProfileGrpcService
import site.iplease.accountserver.lib.ProfileServiceGrpc

@Configuration
class ArmeriaConfig {
    @Bean
    fun armeriaServerConfigurator(service: ProfileGrpcService) = ArmeriaServerConfigurator() {
        ProfileServiceGrpc.SERVICE_NAME
        val grpcService = GrpcService.builder()
            .addService(service)
            .addService(ProtoReflectionService.newInstance())
            .supportedSerializationFormats(GrpcSerializationFormats.values())
            .enableUnframedRequests(true)
            .build()
        it.service(grpcService)
        it.decorator(LoggingService.newDecorator())
        it.serviceUnder("/docs", DocService())
    }
}