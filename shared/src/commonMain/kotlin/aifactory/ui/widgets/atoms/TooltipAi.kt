package aifactory.ui.widgets.feedback

import androidx.compose.runtime.Composable

/**
 * A wrapper that provides a tooltip on hover (on supporting platforms like Desktop).
 * On other platforms, it gracefully does nothing.
 */
@Composable
expect fun TooltipAi(
    tooltip: String?,
    content: @Composable () -> Unit
)
