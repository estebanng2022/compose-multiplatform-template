package aifactory.ui.screens.dashboard

import aifactory.ui.foundation.Announce
import aifactory.ui.foundation.TestTags
import aifactory.ui.foundation.UiStrings
import aifactory.ui.foundation.asButton
import aifactory.ui.foundation.minTouchTarget
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun DashboardList(
    items: List<DashboardItem>,
    isLoadingMore: Boolean,
    canLoadMore: Boolean,
    onLoadMore: () -> Unit,
    onToggleFavorite: (String, Boolean) -> Unit,
    onOpen: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier.testTag(TestTags.Dashboard_List),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { it.id }) { item ->
            DashboardListItem(item = item, onToggleFavorite = onToggleFavorite, onOpen = onOpen)
        }

        if (canLoadMore || isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag(TestTags.Dashboard_LoadMore),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoadingMore) {
                        Announce(message = "Cargando más…")
                        CircularProgressIndicator()
                    } else {
                        Text("Reached end of list.")
                    }
                }
            }
        }
    }

    LaunchedEffect(listState, items, canLoadMore, isLoadingMore) {
        val layoutInfo = listState.layoutInfo
        if (layoutInfo.visibleItemsInfo.isNotEmpty() && canLoadMore && !isLoadingMore) {
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.last().index
            if (lastVisibleItemIndex >= items.size - 3) { 
                onLoadMore()
            }
        }
    }
}

@Composable
private fun DashboardListItem(
    item: DashboardItem,
    onToggleFavorite: (String, Boolean) -> Unit,
    onOpen: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .testTag(TestTags.Dashboard_Item + item.id)
    ) {
        Text(item.title)
        Text(item.subtitle)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .clickable { onToggleFavorite(item.id, !item.isFavorite) }
                    .minTouchTarget()
                    .asButton(selected = item.isFavorite)
                    .semantics(mergeDescendants = true) { 
                        contentDescription = if (item.isFavorite) UiStrings.unfavorite else UiStrings.favorite
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = item.isFavorite,
                    onCheckedChange = null, 
                )
                Text("Favorite") 
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onOpen(item.id) },
                modifier = Modifier
                    .minTouchTarget()
                    .semantics { contentDescription = "${UiStrings.open} ${item.title}" }
            ) {
                Text("Open")
            }
        }
    }
}
