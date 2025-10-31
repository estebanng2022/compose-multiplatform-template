package aifactory.ui.navigation

import aifactory.ui.foundation.Spacing
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A text header for a section of navigation items.
 */
@Composable
fun NavSectionHeader(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(
            start = Spacing.large,
            end = Spacing.large,
            top = Spacing.large,
            bottom = Spacing.xSmall
        )
    )
}
