package aifactory.showcase.screens

import aifactory.ai.AgentStatus
import aifactory.ai.LocalAIManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch
import aifactory.core.Result
import aifactory.ui.theme.LocalSpacing

@Composable
fun AIAgentMonitorScreen(
    localAiManager: LocalAIManager,
) {
    val spacing = LocalSpacing.current
    var agents by remember { mutableStateOf<List<AgentStatus>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun refresh() {
        scope.launch {
            isLoading = true
            errorMessage = null
            when (val result = localAiManager.loadAgents()) {
                is Result.Success -> {
                    agents = result.value
                    isLoading = false
                }
                is Result.Failure -> {
                    errorMessage = result.exception.message ?: "No fue posible leer configs/agents."
                    agents = emptyList()
                    isLoading = false
                }
            }
        }
    }

    LaunchedEffect(localAiManager) {
        refresh()
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.md, vertical = spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.md),
        ) {
            Text(
                text = "Agentes locales",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "La integración escanea configs/agents/*.yaml y muestra el estado de cada AI local.",
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                Button(onClick = { refresh() }) {
                    Text("Actualizar")
                }
            }
            when {
                isLoading -> CircularProgressIndicator()
                errorMessage != null -> Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                )
                agents.isEmpty() -> Text("No se encontraron agentes. Agrega archivos YAML en configs/agents.")
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
                    items(agents) { status ->
                        AgentStatusCard(status)
                    }
                }
            }
        }
    }
}

@Composable
private fun AgentStatusCard(status: AgentStatus) {
    val spacing = LocalSpacing.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
    ) {
        Column(modifier = Modifier.padding(spacing.md), verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            Text(status.profile.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text("Rol: ${status.profile.role}", style = MaterialTheme.typography.bodyMedium)
            Text("Reglas activas: ${status.activeRules.joinToString()}", style = MaterialTheme.typography.bodySmall)
            if (status.profile.tools.isNotEmpty()) {
                Text("Herramientas: ${status.profile.tools.joinToString()}", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = "Última señal: ${status.lastHeartbeat}",
                style = MaterialTheme.typography.labelSmall,
            )
            status.logs.takeIf { it.isNotEmpty() }?.let { logs ->
                Text(
                    text = "Logs:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = spacing.xs),
                )
                logs.forEach { log ->
                    Text(
                        text = "• $log",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

