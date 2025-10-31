package aifactory.domain.models

import aifactory.core.Id
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
enum class RunStatus {
    PENDING, RUNNING, SUCCESS, FAILURE, CANCELLED
}

/**
 * An execution of a Pipeline with state, logs, and timing.
 */
@Serializable
data class Run(
    val id: String = Id.newId("run"),
    val pipelineId: String,
    var status: RunStatus = RunStatus.PENDING,
    val logs: MutableList<String> = mutableListOf(),
    val startedAt: Instant? = null,
    val finishedAt: Instant? = null,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now()
)
