package aifactory.ui.layout.page

import aifactory.ui.foundation.Spacing
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A container that centers its content and applies a max width and standard padding.
 * It's ideal for wrapping the main content of a screen.
 */
@Composable
fun PageContainer(
    modifier: Modifier = Modifier,
    maxWidth: Dp = 1040.dp,
    contentPadding: PaddingValues = PaddingValues(Spacing.xxLarge),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.TopCenter // Align content to the top
    ) {
        Box(
            modifier = Modifier.widthIn(max = maxWidth)
        ) {
            content()
        }
    }
}
