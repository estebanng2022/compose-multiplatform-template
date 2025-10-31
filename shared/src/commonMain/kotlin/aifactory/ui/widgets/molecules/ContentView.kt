package aifactory.ui.widgets.molecules

import aifactory.ui.widgets.feedback.EmptyStateView
import aifactory.ui.widgets.feedback.ErrorStateView
import aifactory.ui.foundation.Spacing
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Base container for the main area: transparent, centers its child
 * and provides common states (loading / empty / error), without its own background.
 */
@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    maxWidth: Dp = 960.dp,
    padding: Dp = Spacing.xxLarge,
    contentAlignment: Alignment = Alignment.Center,
    isLoading: Boolean = false,
    isEmpty: Boolean = false,
    emptyContent: (@Composable () -> Unit)? = null,
    errorMessage: String? = null,
    onRetry: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = contentAlignment
    ) {
        // Content area: centered and with a maximum width, without background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = maxWidth),
            contentAlignment = contentAlignment
        ) {
            Crossfade(
                targetState = when {
                    errorMessage != null -> "error"
                    isEmpty -> "empty"
                    else -> "content"
                },
                label = "ContentViewState"
            ) { state ->
                when (state) {
                    "error" -> ErrorStateView(
                        message = errorMessage ?: "",
                        onRetry = onRetry
                    )
                    "empty" -> emptyContent?.invoke() ?: EmptyStateView()
                    else -> content()
                }
            }
        }

        // Loading overlay (does not change the layout, without background)
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }
    }
}
