package aifactory.ui.sidenav

import androidx.compose.runtime.Composable

@Composable
expect fun TooltipWrapper(tooltip: String?, content: @Composable () -> Unit)
