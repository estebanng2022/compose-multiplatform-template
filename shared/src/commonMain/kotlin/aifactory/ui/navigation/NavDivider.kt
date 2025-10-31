package aifactory.ui.navigation

import aifactory.ui.foundation.Spacing
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A visual divider for navigation items, with standard padding.
 */
@Composable
fun NavDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier.padding(vertical = Spacing.small)
    )
}
