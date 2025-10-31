package aifactory.ui.screens.detail

import aifactory.ui.foundation.TestTags
import aifactory.ui.foundation.UiStrings
import aifactory.ui.foundation.asButton
import aifactory.ui.foundation.minTouchTarget
import aifactory.ui.screens.dashboard.DashboardItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(
    item: DashboardItem,
    onBack: () -> Unit,
    onShare: (String) -> Unit,
    onToggleFavorite: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    // Request focus on the title when the screen is first displayed
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.testTag(TestTags.Detail_Header)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = onBack,
                modifier = Modifier
                    .minTouchTarget()
                    .asButton()
                    .semantics { contentDescription = UiStrings.back }
            ) {
                Text("Back")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onShare("Sharing payload for ${item.id}") },
                modifier = Modifier
                    .minTouchTarget()
                    .testTag(TestTags.Detail_Share)
                    .semantics { contentDescription = UiStrings.share }
            ) {
                Text("Share")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier
                    .clickable { onToggleFavorite(!item.isFavorite) }
                    .minTouchTarget()
                    .testTag(TestTags.Detail_Favorite)
                    .asButton(selected = item.isFavorite)
                    .semantics(mergeDescendants = true) {
                        contentDescription = if (item.isFavorite) UiStrings.unfavorite else UiStrings.favorite
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Favorite")
                Checkbox(
                    checked = item.isFavorite,
                    onCheckedChange = null // Click is handled by the parent Row
                )
            }
        }

        Text(
            text = "Title: ${item.title}",
            modifier = Modifier.focusRequester(focusRequester)
        )
        Text("Subtitle: ${item.subtitle}")
        Text("ID: ${item.id}")
    }
}
