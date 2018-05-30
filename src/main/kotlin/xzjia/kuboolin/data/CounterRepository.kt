package xzjia.kuboolin.data

import reactor.core.publisher.Mono

interface CounterRepository {
    fun up(): Mono<Long>
    fun down(): Mono<Long>
    fun get(): Mono<Long>
}