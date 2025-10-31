package aifactory.ui.screens.pipelines

import aifactory.presentation.state.UiState
import aifactory.presentation.viewmodels.PipelinesViewModel
import aifactory.ui.layout.page.PageHeader
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PipelinesScreen(viewModel: PipelinesViewModel) {
    val state by viewModel.state.collectAsState()

    Column(Modifier.fillMaxSize()) {
        PageHeader(
            title = "Pipelines",
            subtitle = "Manage and run pipelines",
            actions = {
                TextButton(onClick = { viewModel.refresh() }) { Text("Refresh") }
            }
        )
        when (val s = state) {
            is UiState.Loading -> Box(Modifier.fillMaxSize()) { CircularProgressIndicator() }
            is UiState.Empty -> Box(Modifier.fillMaxSize()) { Text(s.reason ?: "No pipelines") }
            is UiState.Error -> Box(Modifier.fillMaxSize()) { Text(s.message) }
            is UiState.Idle -> { /* no-op */ }
            is UiState.Content -> {
                LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
                    items(s.data) { pipeline ->
                        ListItem(
                            headlineContent = { Text(pipeline.name) },
                            supportingContent = { Text("Tasks: ${pipeline.tasks.size}") }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
