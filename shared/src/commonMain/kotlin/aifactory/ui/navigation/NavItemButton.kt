package aifactory.ui.navigation

import aifactory.ui.widgets.feedback.AiBadge
import aifactory.ui.widgets.feedback.TooltipAi
import aifactory.ui.foundation.Spacing
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * The visual representation of a single navigation item.
 * This is an internal component used by NavGroup and other navigation composites.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NavItemButton(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    badgeCount: Int? = null,
    enabled: Boolean = true,
    tooltip: String? = null,
) {
    val bg by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.10f) else Color.Transparent
    )
    val tint by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    )
    val indicatorWidth by animateDpAsState(
        targetValue = if (selected) 3.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )

    TooltipAi(tooltip = tooltip) {
        Row(
            modifier = modifier
                .padding(horizontal = Spacing.small, vertical = Spacing.xSmall)
                .fillMaxWidth()
                .heightIn(min = 44.dp)
                .background(bg, RoundedCornerShape(12.dp))
                .clickable(role = Role.Button, onClick = onClick, enabled = enabled)
                .padding(vertical = Spacing.small, horizontal = Spacing.xSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Active item side indicator
            Box(
                Modifier
                    .width(indicatorWidth)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(6.dp))
            )

            Spacer(Modifier.width(Spacing.small))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                BadgedBox(
                    badge = {
                        if (badgeCount != null) {
                            AiBadge(count = badgeCount)
                        }
                    }
                ) {
                    Icon(icon, contentDescription = label, tint = tint)
                }

                if (showLabel) {
                    Spacer(Modifier.height(Spacing.xSmall))
                    Text(label, fontSize = 11.sp, color = tint, maxLines = 1)
                }
            }
        }
    }
}
