package aifactory.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    isScrollable: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    useSafeInsets: Boolean = true,
    maxWidth: Dp? = 920.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable BoxScope.() -> Unit
) {
    var finalModifier = modifier.fillMaxSize()

    if (useSafeInsets) {
        finalModifier = finalModifier.safeDrawingPadding()
    }

    finalModifier = finalModifier.padding(contentPadding)

    if (isScrollable) {
        finalModifier = finalModifier.verticalScroll(rememberScrollState())
    }

    Box(
        modifier = finalModifier,
        contentAlignment = Alignment.TopCenter // Always align the container to the top
    ) {
        val contentModifier = if (maxWidth != null) {
            Modifier.widthIn(max = maxWidth)
        } else {
            Modifier
        }

        Box(
            modifier = contentModifier.align(Alignment(horizontalAlignment, verticalAlignment)),
            content = content
        )
    }
}
