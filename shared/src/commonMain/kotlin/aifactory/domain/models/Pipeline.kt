package aifactory.domain.models

import aifactory.core.Id
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * A sequence of Tasks that produces a result.
 */
@Serializable
data class Pipeline(
    val id: String = Id.newId("pipeline"),
    val name: String,
    val tasks: List<Task>,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now()
)
