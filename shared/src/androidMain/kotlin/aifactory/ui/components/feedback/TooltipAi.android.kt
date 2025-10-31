package aifactory.ui.components.feedback

import androidx.compose.runtime.Composable

@Composable
actual fun TooltipAi(tooltip: String?, content: @Composable () -> Unit) {
    content()
}
