package aifactory.ui.widgets.page

import aifactory.ui.foundation.Spacing
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A container for a distinct section of content on a page, with its own header and content area.
 */
@Composable
fun PageSection(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                if (description != null) {
                    Spacer(Modifier.height(Spacing.xSmall))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            actions()
        }
        Spacer(Modifier.height(Spacing.large))
        Divider()
        Spacer(Modifier.height(Spacing.large))
        content()
    }
}
