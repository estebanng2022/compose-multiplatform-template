package aifactory.domain.models

import aifactory.core.Id
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * Defines the rule file associated with an Agent or Pipeline.
 */
@Serializable
data class RuleSet(
    val id: String = Id.newId("ruleset"),
    val filePath: String,
    val checksum: String,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now()
)
