package xzjia.kuboolin.api

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.InetAddress

@Component
class GreetingHandler() {
    fun greeting(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
            .ok()
            .body(
                fromObject("こんにちは world, serving from ${InetAddress.getLocalHost().hostName}")
            )
    }
}