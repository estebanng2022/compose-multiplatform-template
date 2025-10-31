package aifactory.core.health

/**
 * Represents a single check within a health report.
 */
data class HealthItem(
    val id: String,
    val label: String,
    val status: HealthStatus,
    val detail: String? = null
)
