package xzjia.kuboolin.model

import java.time.LocalDateTime

data class CounterState(val value: Long, val asOf: LocalDateTime = LocalDateTime.now())

sealed class CounterEvent(val type: String, val at: LocalDateTime = LocalDateTime.now())

data class CounterUp(val value: Long): CounterEvent("UP")

data class CounterDown(val value: Long): CounterEvent("DOWN")