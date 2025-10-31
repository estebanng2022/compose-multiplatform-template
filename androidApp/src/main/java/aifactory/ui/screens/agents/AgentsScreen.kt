package aifactory.ui.screens.agents

import aifactory.presentation.state.UiState
import aifactory.presentation.viewmodels.AgentsViewModel
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
fun AgentsScreen(viewModel: AgentsViewModel, configPath: String) {
    val state by viewModel.state.collectAsState()

    Column(Modifier.fillMaxSize()) {
        PageHeader(
            title = "Agents",
            subtitle = configPath,
            actions = {
                TextButton(onClick = { viewModel.loadAgents(configPath) }) { Text("Reload") }
            }
        )
        when (val s = state) {
            is UiState.Idle -> {
                LaunchedEffectOnce { viewModel.loadAgents(configPath) }
            }
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize()) { CircularProgressIndicator() }
            }
            is UiState.Empty -> {
                Box(Modifier.fillMaxSize()) { Text(text = s.reason ?: "No agents found") }
            }
            is UiState.Error -> {
                Box(Modifier.fillMaxSize()) { Text(text = s.message) }
            }
            is UiState.Content -> {
                LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
                    items(s.data) { agent ->
                        ListItem(
                            headlineContent = { Text(agent.name) },
                            supportingContent = { Text(agent.role) }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
private fun LaunchedEffectOnce(block: () -> Unit) {
    androidx.compose.runtime.LaunchedEffect(Unit) { block() }
}
