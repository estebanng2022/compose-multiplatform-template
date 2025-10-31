package aifactory.presentation.viewmodels

import aifactory.core.Result
import aifactory.presentation.state.ProjectWizardState
import aifactory.presentation.state.ThemeEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface ThemeLister {
    suspend fun list(projectId: String? = null): Result<List<ThemeEntry>>
}

class ProjectWizardViewModel(
    private val lister: ThemeLister,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _state = MutableStateFlow(ProjectWizardState())
    val state: StateFlow<ProjectWizardState> = _state

    fun start(projectId: String) {
        _state.value = ProjectWizardState(projectId = projectId, isLoading = true)
        scope.launch {
            val res = lister.list(projectId)
            when (res) {
                is Result.Failure -> _state.value = _state.value.copy(isLoading = false, error = res.exception.message)
                is Result.Success -> _state.value = _state.value.copy(isLoading = false, themes = res.value)
            }
        }
    }

    fun select(theme: ThemeEntry) { _state.value = _state.value.copy(selectedTheme = theme) }
}

