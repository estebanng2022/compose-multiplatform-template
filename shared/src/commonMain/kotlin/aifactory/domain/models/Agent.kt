package aifactory.domain.models

import aifactory.core.Id
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * Represents a local AI agent with its role, rules, and tools.
 */
@Serializable
data class Agent(
    val id: String = Id.newId("agent"),
    val name: String,
    val role: String,
    val rules: RuleSet,
    val tools: List<String>,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now()
)
