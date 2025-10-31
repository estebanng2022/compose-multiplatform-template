package aifactory.presentation.viewmodels

import aifactory.presentation.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _state = MutableStateFlow<UiState<Map<String, String>>>(UiState.Idle)
    val state: StateFlow<UiState<Map<String, String>>> = _state
}


