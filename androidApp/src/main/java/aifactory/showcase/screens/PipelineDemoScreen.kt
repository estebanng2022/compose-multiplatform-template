package aifactory.showcase.screens

import aifactory.ai.PipelineSimulation
import aifactory.ai.RuleEngine
import aifactory.ai.StepStatus
import aifactory.core.Result
import aifactory.domain.models.Pipeline
import aifactory.domain.models.Task
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch
import aifactory.ui.theme.LocalSpacing

@Composable
fun PipelineDemoScreen(
    ruleEngine: RuleEngine,
) {
    val spacing = LocalSpacing.current
    val pipeline = remember { samplePipeline() }
    var simulation by remember { mutableStateOf<PipelineSimulation?>(null) }
    var isRunning by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun runSimulation() {
        scope.launch {
            isRunning = true
            error = null
            when (val result = ruleEngine.simulatePipeline(pipeline)) {
                is Result.Success -> simulation = result.value
                is Result.Failure -> {
                    simulation = null
                    error = result.exception.message ?: "No se pudo ejecutar el pipeline de muestra."
                }
            }
            isRunning = false
        }
    }

    LaunchedEffect(ruleEngine) {
        runSimulation()
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.md, vertical = spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.md),
        ) {
            Text(
                text = "Pipeline Demo",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Simula una ejecución de pipeline usando los agentes locales detectados.",
                style = MaterialTheme.typography.bodyMedium,
            )
            Button(onClick = { runSimulation() }) {
                Text("Volver a ejecutar")
            }
            when {
                isRunning -> CircularProgressIndicator()
                error != null -> Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                )
                simulation == null -> Text("Sin resultados todavía.")
                else -> PipelineResult(simulation!!)
            }
        }
    }
}

@Composable
private fun PipelineResult(simulation: PipelineSimulation) {
    val spacing = LocalSpacing.current
    Text(
        text = "Pipeline: ${simulation.pipelineName}",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
    )
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {
        items(simulation.steps) { step ->
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))) {
                Column(modifier = Modifier.padding(spacing.md), verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
                    Text("Paso ${step.stepIndex + 1}: ${step.task.name}", fontWeight = FontWeight.SemiBold)
                    Text(step.task.description, style = MaterialTheme.typography.bodySmall)
                    Text("Agente asignado: ${step.agent?.name ?: "Sin asignar"}", style = MaterialTheme.typography.bodySmall)
                    Text(
                        text = "Estado: ${labelForStatus(step.status)}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = step.output,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }
    }
}

private fun labelForStatus(status: StepStatus): String = when (status) {
    StepStatus.Pending -> "Pendiente"
    StepStatus.Running -> "En progreso"
    StepStatus.Success -> "Completado"
    StepStatus.Failure -> "Falló"
}

private fun samplePipeline(): Pipeline {
    val tasks = listOf(
        Task(
            name = "Auditar UI",
            description = "Valida accesibilidad y tokens premium.",
            action = "validate:ui",
            params = mapOf("validator" to "premium"),
        ),
        Task(
            name = "Generar snapshots",
            description = "Ejecuta snapshots para detectar regresiones visuales.",
            action = "validate:snapshots",
            params = emptyMap(),
        ),
        Task(
            name = "Empaquetar Android",
            description = "Construye el bundle Android listo para distribución.",
            action = "package:android",
            params = mapOf("variant" to "release"),
        ),
        Task(
            name = "Exportar ZIP",
            description = "Empaqueta el proyecto completo en artifacts/exports.",
            action = "package:zip",
            params = mapOf("target" to "artifacts/exports/project.zip"),
        ),
    )
    return Pipeline(name = "Pipeline Premium", tasks = tasks)
}

