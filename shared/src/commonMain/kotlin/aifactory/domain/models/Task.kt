package aifactory.domain.models

import aifactory.core.Id
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * Represents a unit of work (e.g., create file, compile, generate code).
 */
@Serializable
data class Task(
    val id: String = Id.newId("task"),
    val name: String,
    val description: String,
    val action: String, // e.g., "file:create", "shell:exec"
    val params: Map<String, String>,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now()
)
