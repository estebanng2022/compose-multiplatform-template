package aifactory.ui.widgets.feedback

import aifactory.ui.foundation.Spacing
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A default view for representing an empty state (e.g., no data).
 */
@Composable
fun EmptyStateView() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "No content yet",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(Spacing.small))
        Text(
            "Start by creating something in the left panel.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * A default view for representing an error state.
 */
@Composable
fun ErrorStateView(
    message: String,
    onRetry: (() -> Unit)?
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Something went wrong",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.height(Spacing.xSmall))
        Text(
            message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (onRetry != null) {
            Spacer(Modifier.height(Spacing.medium))
            Button(onClick = onRetry) { Text("Retry") }
        }
    }
}
