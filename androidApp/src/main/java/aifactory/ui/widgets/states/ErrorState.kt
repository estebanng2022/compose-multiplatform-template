package aifactory.ui.widgets.states

import aifactory.ui.foundation.TestTags
import aifactory.ui.foundation.UiStrings
import aifactory.ui.foundation.asButton
import aifactory.ui.foundation.minTouchTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun ErrorState(message: String, onRetry: (() -> Unit)? = null, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.State_Error)
            .semantics(mergeDescendants = true) { contentDescription = UiStrings.error },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Error: $message")
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRetry,
                modifier = Modifier
                    .minTouchTarget()
                    .asButton()
                    .testTag(TestTags.Button_Retry)
                    .semantics { contentDescription = UiStrings.retry }
            ) {
                Text("Retry")
            }
        }
    }
}
