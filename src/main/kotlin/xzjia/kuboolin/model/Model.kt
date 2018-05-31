package xzjia.kuboolin.model

import java.net.InetAddress
import java.time.LocalDateTime

data class CounterState(val value: Long, val asOf: LocalDateTime = LocalDateTime.now(), val by: String = InetAddress.getLocalHost().hostName)

sealed class CounterEvent(val type: String, val at: LocalDateTime = LocalDateTime.now())

data class CounterUp(val value: Long): CounterEvent("UP")

data class CounterDown(val value: Long): CounterEvent("DOWN")
