package aifactory.ui.navigation

import aifactory.ui.foundation.Spacing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A composable that displays a side navigation rail, built from modular navigation components.
 * It can be collapsed or expanded.
 */
@Composable
fun SideNavigation(
    modifier: Modifier = Modifier,
    mainItems: List<NavItem>,
    bottomActionItems: List<NavItem> = emptyList(),
    selectedId: String?,
    onItemSelected: (String) -> Unit,
    collapsed: Boolean,
    widthExpanded: Dp = 256.dp,
    widthCollapsed: Dp = 80.dp,
    header: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
) {
    val width by animateDpAsState(
        targetValue = if (collapsed) widthCollapsed else widthExpanded,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )

    Surface(
        modifier = modifier.width(width).fillMaxHeight(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(vertical = Spacing.small),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if (header != null) {
                    header()
                }
                NavGroup(
                    items = mainItems,
                    selectedId = selectedId,
                    onItemSelected = onItemSelected,
                    showLabels = !collapsed
                )
            }

            Column {
                NavGroup(
                    items = bottomActionItems,
                    selectedId = null, // Actions are not selectable
                    onItemSelected = onItemSelected,
                    showLabels = !collapsed
                )
                if (footer != null) {
                    footer()
                }
            }
        }
    }
}
