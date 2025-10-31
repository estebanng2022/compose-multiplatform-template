package aifactory.ui.sidenav

import androidx.compose.runtime.Composable

@Composable
actual fun TooltipWrapper(tooltip: String?, content: @Composable () -> Unit) {
    content()
}
