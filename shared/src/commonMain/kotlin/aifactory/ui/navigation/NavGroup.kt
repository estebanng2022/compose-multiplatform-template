package aifactory.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A container for a group of navigation items, with an optional header.
 */
@Composable
fun NavGroup(
    modifier: Modifier = Modifier,
    title: String? = null,
    items: List<NavItem>,
    selectedId: String?,
    onItemSelected: (String) -> Unit,
    showLabels: Boolean = true
) {
    Column(modifier = modifier) {
        if (title != null) {
            NavSectionHeader(text = title)
        }
        items.forEach { item ->
            NavItemButton(
                icon = item.icon,
                label = item.label,
                selected = item.id == selectedId,
                onClick = { onItemSelected(item.id) },
                badgeCount = item.badgeCount,
                showLabel = showLabels,
                enabled = item.enabled,
                tooltip = if (showLabels) null else item.tooltip // Only show tooltip when label is hidden
            )
        }
    }
}
