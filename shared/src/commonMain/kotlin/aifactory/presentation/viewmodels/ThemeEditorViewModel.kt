package aifactory.presentation.viewmodels

import aifactory.core.Result
import aifactory.data.repositories.ThemeRepository
import aifactory.domain.models.Theme
import aifactory.domain.usecases.ApplyThemeToProject
import aifactory.domain.usecases.CreateTheme
import aifactory.domain.usecases.ValidateTheme
import aifactory.presentation.state.ThemeEditorState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeEditorViewModel(
    private val repository: ThemeRepository,
    private val create: CreateTheme,
    private val validateUse: ValidateTheme,
    private val applyUse: ApplyThemeToProject,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _state = MutableStateFlow(ThemeEditorState())
    val state: StateFlow<ThemeEditorState> = _state

    fun load(projectId: String, themeName: String) {
        _state.value = _state.value.copy(isLoading = true, error = null)
        scope.launch {
            when (val res = repository.get(projectId, themeName)) {
                is Result.Failure -> _state.value = _state.value.copy(isLoading = false, error = res.exception.message)
                is Result.Success -> _state.value = _state.value.copy(isLoading = false, theme = res.value)
            }
        }
    }

    fun save(theme: Theme, projectId: String?) {
        _state.value = _state.value.copy(isLoading = true, error = null)
        scope.launch {
            when (val res = if (projectId == null) create(theme) else repository.save(theme, projectId)) {
                is Result.Failure -> _state.value = _state.value.copy(isLoading = false, error = res.exception.message)
                is Result.Success -> _state.value = _state.value.copy(isLoading = false, theme = theme)
            }
        }
    }

    fun validate(theme: Theme) {
        scope.launch {
            when (val res = validateUse(theme)) {
                is Result.Failure -> _state.value = _state.value.copy(error = res.exception.message)
                is Result.Success -> _state.value = _state.value.copy(validationIssues = if (res.value.passed) emptyList() else res.value.issues)
            }
        }
    }

    fun apply(projectId: String, themeName: String) {
        _state.value = _state.value.copy(isLoading = true, error = null)
        scope.launch {
            when (val res = applyUse(projectId, themeName)) {
                is Result.Failure -> _state.value = _state.value.copy(isLoading = false, error = res.exception.message)
                is Result.Success -> _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}

