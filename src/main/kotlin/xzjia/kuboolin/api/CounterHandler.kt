package xzjia.kuboolin.api

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.Mono
import xzjia.kuboolin.data.CounterRepository
import xzjia.kuboolin.model.CounterDown
import xzjia.kuboolin.model.CounterState
import xzjia.kuboolin.model.CounterUp
import xzjia.kuboolin.service.EventBus

@Component

class CounterHandler(private val eventBus: EventBus, private val counterRepository: CounterRepository) {
    fun get(serverRequest: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                .ok()
                .body(
                    counterRepository.get()
                        .map {CounterState(it)}
                )

    fun up(serverRequest: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                .ok()
                .body(
                    counterRepository.up()
                        .map { CounterState(it) }
                        .doOnNext { eventBus.publish(CounterUp(it.value)) }
                )


    fun down(serverRequest: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                .ok()
                .body(
                    counterRepository.down()
                        .map { CounterState(it) }
                        .doOnNext { eventBus.publish(CounterDown(it.value)) }
                )

    fun stream(serverRequest: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                .ok()
                .bodyToServerSentEvents(eventBus.subscribe())

}
