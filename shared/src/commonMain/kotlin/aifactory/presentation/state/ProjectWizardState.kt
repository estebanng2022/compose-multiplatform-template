package aifactory.presentation.state

data class ProjectWizardState(
    val projectId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val themes: List<ThemeEntry> = emptyList(),
    val selectedTheme: ThemeEntry? = null,
)

data class ThemeEntry(
    val project: String,
    val name: String,
    val path: String,
    val scope: String, // global | project
)

