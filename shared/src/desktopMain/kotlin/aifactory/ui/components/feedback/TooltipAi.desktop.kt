package aifactory.ui.widgets.feedback

import aifactory.ui.foundation.Spacing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun TooltipAi(tooltip: String?, content: @Composable () -> Unit) {
    if (tooltip.isNullOrBlank()) {
        content()
        return
    }
    TooltipArea(
        tooltip = {
            Surface(
                shape = MaterialTheme.shapes.extraSmall,
                shadowElevation = Spacing.xSmall
            ) {
                Text(
                    text = tooltip,
                    modifier = Modifier.padding(horizontal = Spacing.small, vertical = Spacing.xSmall)
                )
            }
        }
    ) { content() }
}
