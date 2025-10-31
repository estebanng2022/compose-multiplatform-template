package aifactory.ui.navigation

import aifactory.ui.foundation.Spacing
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A toggle button to collapse or expand the navigation rail.
 */
@Composable
fun NavRailToggle(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean,
    onToggle: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.small),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onToggle) {
            val icon = if (isCollapsed) Icons.Default.ChevronRight else Icons.Default.ChevronLeft
            val description = if (isCollapsed) "Expand Navigation" else "Collapse Navigation"
            Icon(imageVector = icon, contentDescription = description)
        }
    }
}
