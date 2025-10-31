package aifactory.ui.widgets.feedback

import androidx.compose.runtime.Composable

@Composable
fun TooltipAi(tooltip: String?, content: @Composable () -> Unit) {
    content()
}
