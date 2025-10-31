package aifactory.ui.widgets.states

import aifactory.ui.foundation.TestTags
import aifactory.ui.foundation.UiStrings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun EmptyState(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.State_Empty)
            .semantics(mergeDescendants = true) { contentDescription = UiStrings.empty },
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}
