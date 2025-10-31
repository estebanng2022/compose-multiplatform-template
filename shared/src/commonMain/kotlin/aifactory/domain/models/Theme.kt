package aifactory.domain.models

import aifactory.core.Id
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Theme(
    val id: String = Id.newId("theme"),
    val name: String,
    val scope: String = "project", // or "global"
    val tokens: ThemeTokens,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
)
