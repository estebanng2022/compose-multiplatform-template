package aifactory.ui.screens.settings

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import aifactory.core.Config
import aifactory.core.Result
import aifactory.ui.widgets.molecules.PageHeader
import aifactory.ui.theme.LocalSpacing
import aifactory.ui.theme.LocalThemeVariant
import aifactory.ui.theme.ThemeVariant
import aifactory.presentation.validation.ThemeValidator
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun SettingsScreen() {
    val spacing = LocalSpacing.current
    val themeVariant = LocalThemeVariant.current
    val context = LocalContext.current
    val editor = remember { ConfigEditor(context) }
    val scope = rememberCoroutineScope()

    var agents by remember { mutableStateOf(listOf<FileEntry>()) }
    var selectedAgent by remember { mutableStateOf<FileEntry?>(null) }
    var agentContent by remember { mutableStateOf("") }
    var pipelinesContent by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var themes by remember { mutableStateOf(listOf<ThemeFile>()) }
    var selectedTheme by remember { mutableStateOf<ThemeFile?>(null) }
    var themeContent by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        agents = editor.listAgents()
        pipelinesContent = editor.readText(Config.Paths.PIPELINES_FILE).getOrElse("")
        themes = editor.listThemes()
    }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        PageHeader(title = "Settings", subtitle = "Design system and preferences")

        // Theme selector
        Column(Modifier.fillMaxWidth().padding(spacing.md), verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
            Text("Theme", fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                listOf(ThemeVariant.Premium, ThemeVariant.Minimal, ThemeVariant.Animalita).forEach { variant ->
                    TextButton(onClick = { themeVariant.value = variant }) {
                        val marker = if (themeVariant.value == variant) "●" else "○"
                        Text("$marker ${variant.name}")
                    }
                }
            }
        }

        Spacer(Modifier.height(spacing.md))

        // Config editor: Agents
        Column(Modifier.fillMaxWidth().padding(spacing.md), verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
            Text("Configs: Agents", fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                Button(onClick = { agents = editor.listAgents() }) { Text("Refrescar") }
                Button(onClick = {
                    val file = editor.newAgentFileName()
                    selectedAgent = file
                    agentContent = "id: ${file.nameWithoutExtension()}\nname: New Agent\nrole: Assistant\nrules: configs/rules/${file.nameWithoutExtension()}.yaml\ntools: []\n"
                }) { Text("Nuevo") }
            }
            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(0.35f), verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
                    agents.forEach { file ->
                        TextButton(onClick = {
                            selectedAgent = file
                            scope.launch {
                                agentContent = editor.readText(file.path).getOrElse("")
                            }
                        }) { Text(file.name) }
                    }
                }
                Column(Modifier.weight(0.65f).padding(start = spacing.md)) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth().height(240.dp),
                        value = agentContent,
                        onValueChange = { agentContent = it },
                        label = { Text(selectedAgent?.name ?: "agent.yaml") }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        Button(onClick = {
                            val path = selectedAgent?.path ?: return@Button
                            scope.launch {
                                when (val res = editor.writeText(path, agentContent)) {
                                    is Result.Success -> message = "Agente guardado"
                                    is Result.Failure -> message = res.exception.message
                                }
                            }
                        }, enabled = selectedAgent != null) { Text("Guardar") }
                    }
                    message?.let { Text(it) }
                }
            }
        }

        Spacer(Modifier.height(spacing.md))

        // Config editor: Pipelines
        Column(Modifier.fillMaxWidth().padding(spacing.md), verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
            Text("Configs: Pipelines", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(240.dp),
                value = pipelinesContent,
                onValueChange = { pipelinesContent = it },
                label = { Text(Config.Paths.PIPELINES_FILE) }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                Button(onClick = {
                    scope.launch {
                        when (val res = editor.writeText(Config.Paths.PIPELINES_FILE, pipelinesContent)) {
                            is Result.Success -> message = "Pipelines guardados"
                            is Result.Failure -> message = res.exception.message
                        }
                    }
                }) { Text("Guardar") }
                Button(onClick = {
                    scope.launch {
                        pipelinesContent = editor.readText(Config.Paths.PIPELINES_FILE).getOrElse("")
                    }
                }) { Text("Recargar") }
            }
        }

        Spacer(Modifier.height(spacing.md))

        // Project Themes (CRUD + Validate)
        Column(Modifier.fillMaxWidth().padding(spacing.md), verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
            Text("Settings → Design System → Project Themes", fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                Button(onClick = { themes = editor.listThemes() }) { Text("Refrescar") }
                Button(onClick = {
                    val f = editor.newThemeFileName(project = "demo", theme = "theme-${themes.size + 1}")
                    selectedTheme = f
                    themeContent = """
                        |# Minimal theme.yaml
                        |primary: "#6366F1"
                        |secondary: "#A855F7"
                        |background: "#0F172A"
                        |surface: "#111827"
                    """.trimMargin()
                }) { Text("Nuevo Theme") }
            }
            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(0.35f), verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
                    themes.forEach { file ->
                        TextButton(onClick = {
                            selectedTheme = file
                            scope.launch { themeContent = editor.readText(file.path).getOrElse("") }
                        }) { Text(file.displayName()) }
                    }
                }
                Column(Modifier.weight(0.65f).padding(start = spacing.md)) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth().height(240.dp),
                        value = themeContent,
                        onValueChange = { themeContent = it },
                        label = { Text(selectedTheme?.displayName() ?: "theme.yaml") }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
                        Button(onClick = {
                            val path = selectedTheme?.path ?: return@Button
                            scope.launch {
                                when (val res = editor.writeText(path, themeContent)) {
                                    is Result.Success -> message = "Theme guardado"
                                    is Result.Failure -> message = res.exception.message
                                }
                            }
                        }, enabled = selectedTheme != null) { Text("Guardar") }
                        Button(onClick = {
                            val report = ThemeValidator.validateYaml(themeContent)
                            message = if (report.passed) "Theme válido (AA)" else "Fallos: ${report.issues.joinToString()}"
                        }, enabled = themeContent.isNotBlank()) { Text("Validar") }
                    }
                    message?.let { Text(it) }
                }
            }
        }
    }
}

// --- Helpers ---

private class ConfigEditor(private val context: Context) {
    private val baseDir: File get() = context.filesDir

    fun listAgents(): List<FileEntry> {
        val dir = File(baseDir, Config.Paths.AGENTS_DIR)
        if (!dir.exists()) dir.mkdirs()
        return dir.listFiles { f -> f.isFile && f.extension.equals("yaml", true) }
            ?.sortedBy { it.name }
            ?.map { FileEntry(it.name, relativePath(it)) }
            ?: emptyList()
    }

    fun newAgentFileName(): FileEntry {
        val dir = File(baseDir, Config.Paths.AGENTS_DIR)
        if (!dir.exists()) dir.mkdirs()
        var idx = 1
        while (true) {
            val f = File(dir, "agent-$idx.yaml")
            if (!f.exists()) return FileEntry(f.name, relativePath(f))
            idx++
        }
    }

    suspend fun readText(path: String): Result<String> = aifactory.core.AndroidFileIO(context).read(path)
    suspend fun writeText(path: String, content: String): Result<Unit> = aifactory.core.AndroidFileIO(context).write(path, content)

    private fun relativePath(file: File): String {
        val base = baseDir.toPath()
        val abs = file.toPath()
        return base.relativize(abs).toString().replace('\\', '/')
    }
}

private data class FileEntry(val name: String, val path: String) {
    fun nameWithoutExtension(): String = name.substringBefore('.')
}

private data class ThemeFile(val project: String, val theme: String, val path: String) {
    fun displayName(): String = "$project/$theme"
}

private fun ConfigEditor.listThemes(): List<ThemeFile> {
    val root = File(baseDir, "shared/ui/themes")
    if (!root.exists()) root.mkdirs()
    return root.listFiles { f -> f.isDirectory && f.name != "seeds" }
        ?.flatMap { projectDir ->
            projectDir.listFiles { tf -> tf.isDirectory }?.mapNotNull { themeDir ->
                val themeYaml = File(themeDir, "theme.yaml")
                if (themeYaml.exists()) ThemeFile(projectDir.name, themeDir.name, relativePath(themeYaml)) else null
            } ?: emptyList()
        }
        ?: emptyList()
}

private fun ConfigEditor.newThemeFileName(project: String, theme: String): ThemeFile {
    val dir = File(baseDir, "shared/ui/themes/$project/$theme")
    dir.mkdirs()
    val yaml = File(dir, "theme.yaml")
    return ThemeFile(project, theme, relativePath(yaml))
}
