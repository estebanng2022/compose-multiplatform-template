package aifactory.domain.models

import aifactory.core.Id
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: String = Id.newId("question"),
    val text: String,
    val category: String,
    val required: Boolean = true,
    val help: String? = null,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
)
