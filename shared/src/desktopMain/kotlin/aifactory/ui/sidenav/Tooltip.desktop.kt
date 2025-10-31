package aifactory.ui.sidenav

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun TooltipWrapper(tooltip: String?, content: @Composable () -> Unit) {
    if (tooltip.isNullOrBlank()) { content(); return }
    TooltipArea(
        tooltip = {
            Surface(shape = MaterialTheme.shapes.extraSmall) {
                Text(tooltip, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            }
        }
    ) { content() }
}
