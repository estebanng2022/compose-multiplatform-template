package aifactory.ui.screens.login

import aifactory.core.ServiceLocator
import aifactory.data.AiRepository
import aifactory.ui.widgets.states.AppState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repo: AiRepository = ServiceLocator.repository()
) : ViewModel() {

    private val _state = MutableStateFlow<AppState<Unit>>(AppState.Content(Unit)) // Start in a neutral state
    val state: StateFlow<AppState<Unit>> = _state

    fun submit(user: String, pass: String) {
        viewModelScope.launch {
            _state.value = AppState.Loading
            runCatching { repo.login(user, pass) }
                .onSuccess { success ->
                    _state.value = if (success) AppState.Content(Unit) else AppState.Error("Invalid credentials")
                }
                .onFailure { throwable ->
                    _state.value = AppState.Error("Login failed", throwable)
                }
        }
    }
}
