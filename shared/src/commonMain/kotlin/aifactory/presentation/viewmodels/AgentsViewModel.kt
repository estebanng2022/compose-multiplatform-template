package aifactory.presentation.viewmodels

import aifactory.core.Result
import aifactory.data.repositories.AgentRepository
import aifactory.presentation.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import aifactory.domain.models.Agent
import aifactory.domain.usecases.RegisterAgent

class AgentsViewModel(
    private val repository: AgentRepository,
    private val registerAgent: RegisterAgent? = null
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _state = MutableStateFlow<UiState<List<Agent>>>(UiState.Idle)
    val state: StateFlow<UiState<List<Agent>>> = _state

    fun loadAgents(configPath: String) {
        _state.value = UiState.Loading
        scope.launch {
            when (val res = repository.getAgentsFromConfig(configPath)) {
                is Result.Failure -> _state.value = UiState.Error("Failed to load agents", res.exception)
                is Result.Success -> _state.value = if (res.value.isEmpty()) UiState.Empty() else UiState.Content(res.value)
            }
        }
    }

    fun register(configPath: String) {
        val usecase = registerAgent ?: return
        _state.value = UiState.Loading
        scope.launch {
            when (val res = usecase(configPath)) {
                is Result.Failure -> _state.value = UiState.Error("Failed to register agent", res.exception)
                is Result.Success -> loadAgents(configPath)
            }
        }
    }
}


