package aifactory.core

import kotlinx.datetime.Instant

interface Clock {
    fun now(): Instant
}

/**
 * A default implementation of the Clock that returns the current system time.
 */
object SystemClock : Clock {
    override fun now(): Instant = kotlinx.datetime.Clock.System.now()
}
