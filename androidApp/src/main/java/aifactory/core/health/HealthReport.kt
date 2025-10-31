package aifactory.core.health

/**
 * Represents a full health check report, containing multiple check items.
 */
data class HealthReport(
    val timestampMillis: Long,
    val items: List<HealthItem>
)
