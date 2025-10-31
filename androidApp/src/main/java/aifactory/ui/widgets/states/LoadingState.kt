package aifactory.ui.widgets.states

import aifactory.ui.foundation.Announce
import aifactory.ui.foundation.TestTags
import aifactory.ui.foundation.UiStrings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Announce(UiStrings.loading)
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.State_Loading)
            .semantics { contentDescription = UiStrings.loading },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
