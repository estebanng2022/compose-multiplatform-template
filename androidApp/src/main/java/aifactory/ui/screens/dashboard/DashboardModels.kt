package aifactory.ui.screens.dashboard

import kotlinx.serialization.Serializable

/**
 * UI model for a single item displayed on the dashboard.
 */
@Serializable
data class DashboardItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val isFavorite: Boolean = false
)

/**
 * Represents a single page of dashboard items, including a cursor for the next page.
 */
@Serializable
data class DashboardPage(
    val items: List<DashboardItem>,
    val nextCursor: String? // null means no more pages
)
