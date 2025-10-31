package aifactory.ui.screens.runs

import aifactory.presentation.state.UiState
import aifactory.presentation.viewmodels.RunsViewModel
import aifactory.ui.widgets.molecules.PageHeader
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
fun RunsScreen(viewModel: RunsViewModel, pipelineId: String) {
    val state by viewModel.state.collectAsState()

    Column(Modifier.fillMaxSize()) {
        PageHeader(
            title = "Runs",
            subtitle = "Pipeline: $pipelineId",
            actions = {
                TextButton(onClick = { viewModel.loadForPipeline(pipelineId) }) { Text("Refresh") }
            }
        )
        when (val s = state) {
            is UiState.Loading -> Box(Modifier.fillMaxSize()) { CircularProgressIndicator() }
            is UiState.Empty -> Box(Modifier.fillMaxSize()) { Text(s.reason ?: "No runs") }
            is UiState.Error -> Box(Modifier.fillMaxSize()) { Text(s.message) }
            is UiState.Idle -> { /* no-op */ }
            is UiState.Content -> {
                LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
                    items(s.data) { run ->
                        ListItem(
                            headlineContent = { Text("Run ${run.id}") },
                            supportingContent = { Text(run.status.name) }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
