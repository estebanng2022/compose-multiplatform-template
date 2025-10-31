package aifactory.ui.screens.settings

import aifactory.core.ServiceLocator
import aifactory.data.AiRepository
import aifactory.ui.widgets.states.AppState
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AiRepository = ServiceLocator.repository(application.applicationContext)

    private val _state = MutableStateFlow<AppState<Map<String, Boolean>>>(AppState.Loading)
    val state: StateFlow<AppState<Map<String, Boolean>>> = _state

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = AppState.Loading
            runCatching { repo.fetchSettings() }
                .onSuccess { data ->
                    _state.value = if (data.isEmpty()) AppState.Empty("No settings found") else AppState.Content(data)
                }
                .onFailure { throwable ->
                    _state.value = AppState.Error("Failed to load settings", throwable)
                }
        }
    }

    fun toggle(key: String, value: Boolean) {
        viewModelScope.launch {
            // Optimistically update the UI
            if (_state.value is AppState.Content) {
                val currentSettings = (_state.value as AppState.Content<Map<String, Boolean>>).data.toMutableMap()
                currentSettings[key] = value
                _state.value = AppState.Content(currentSettings)
            }
            
            // Persist the change
            runCatching { (repo as? aifactory.data.mock.InMemoryAiRepository)?.toggle(key, value) }
                .onFailure { throwable ->
                    _state.value = AppState.Error("Failed to save setting", throwable)
                    // Optionally, revert the optimistic update here
                }
        }
    }
}
