package aifactory.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import aifactory.ui.navigation.NavItem

/**
 * Defines the application's navigation structure.
 * This is the single source of truth for destinations.
 */
object AppNavigation {
    val mainNavItems = listOf(
        NavItem(id = "home", icon = Icons.Default.Home, label = "Home", badgeCount = 5),
        NavItem(id = "search", icon = Icons.Default.Search, label = "Search"),
        NavItem(id = "chat", icon = Icons.Default.Chat, label = "Chat"),
        NavItem(id = "settings", icon = Icons.Default.Settings, label = "Settings"),
    )

    val bottomNavActions = listOf(
        NavItem(id = "theme", icon = Icons.Default.LightMode, label = "Theme"),
        NavItem(id = "logout", icon = Icons.Default.Logout, label = "Logout"),
    )
}
