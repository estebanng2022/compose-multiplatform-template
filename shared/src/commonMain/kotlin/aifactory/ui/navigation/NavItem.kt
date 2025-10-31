package aifactory.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data model for a single navigation item used throughout the application.
 * This is the single source of truth for a navigation destination's properties.
 */
data class NavItem(
    val id: String,
    val icon: ImageVector,
    val label: String,
    val tooltip: String = label, // Defaults to the label text
    val badgeCount: Int? = null,
    val enabled: Boolean = true
)
