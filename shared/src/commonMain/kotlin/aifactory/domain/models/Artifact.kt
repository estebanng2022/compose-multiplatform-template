package aifactory.domain.models

import aifactory.core.Id
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * A tangible result (ZIP, snapshot, log, build, etc.).
 */
@Serializable
data class Artifact(
    val id: String = Id.newId("artifact"),
    val runId: String,
    val name: String,
    val path: String, // Path to the generated artifact
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now()
)
