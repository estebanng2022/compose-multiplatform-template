package aifactory.ui.screens.wizard

import aifactory.core.AndroidFileIO
import aifactory.core.Result
import aifactory.data.AndroidThemeLister
import aifactory.presentation.state.ProjectWizardState
import aifactory.presentation.state.ThemeEntry
import aifactory.presentation.viewmodels.ProjectWizardViewModel
import aifactory.ui.widgets.EmptyStateWithAction
import aifactory.ui.widgets.ThemePreviewPanel
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun ProjectWizardThemeStep(
    projectId: String,
    onOpenSettings: () -> Unit,
    onNext: (selectedTheme: ThemeEntry) -> Unit,
) {
    val context = LocalContext.current
    val vm = remember { ProjectWizardViewModel(AndroidThemeLister(context.filesDir)) }
    val state by vm.state.collectAsState()

    LaunchedEffect(projectId) { vm.start(projectId) }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Selecciona un Theme", style = MaterialTheme.typography.titleLarge)
        when {
            state.isLoading -> Text("Cargando themes…")
            state.error != null -> Text("Error: ${state.error}")
            state.themes.isEmpty() -> EmptyStateWithAction(
                message = "No tienes themes de proyecto.",
                actionLabel = "Abrir Project Themes en Settings",
                onAction = onOpenSettings,
            )
            else -> ThemeList(state = state, onSelect = vm::select)
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val selected = state.selectedTheme ?: return@Button
                persistBlueprint(context, projectId, selected)
                onNext(selected)
            }, enabled = state.selectedTheme != null,
                modifier = Modifier
                    .height(48.dp)
                    .semantics { contentDescription = "Siguiente paso: Theme" }
            ) { Text("Siguiente") }
        }
    }
}

@Composable
private fun ThemeList(state: ProjectWizardState, onSelect: (ThemeEntry) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(state.themes) { entry ->
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))) {
                Column(Modifier.fillMaxWidth().padding(12.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("${entry.project}/${entry.name}")
                        Text(entry.scope)
                    }
                    // Minimal preview (static colors; real preview requiere parse del YAML)
                    ThemePreviewPanel(primary = Color(0xFF6366F1), secondary = Color(0xFFA855F7), background = Color(0xFF0F172A), surface = Color(0xFF111827))
                    TextButton(onClick = { onSelect(entry) }) { Text("Seleccionar") }
                }
            }
        }
    }
}

private fun persistBlueprint(context: Context, projectId: String, selected: ThemeEntry) {
    val io = AndroidFileIO(context)
    val dir = "configs/projects/$projectId"
    val file = "$dir/blueprint.yaml"
    val content = "theme: ${selected.name}\n"
    // Ensure dir exists
    File(context.filesDir, dir).mkdirs()
    when (val res = kotlinx.coroutines.runBlocking { io.write(file, content) }) {
        is Result.Failure -> throw res.exception
        is Result.Success -> {}
    }
}
