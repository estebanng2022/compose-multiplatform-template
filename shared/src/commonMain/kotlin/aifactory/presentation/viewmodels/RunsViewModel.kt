package aifactory.presentation.viewmodels

import aifactory.core.Result
import aifactory.data.repositories.RunRepository
import aifactory.domain.models.Run
import aifactory.presentation.state.UiState
import aifactory.domain.usecases.MonitorRun
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RunsViewModel(
    private val repository: RunRepository,
    private val monitorRun: MonitorRun? = null
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _state = MutableStateFlow<UiState<List<Run>>>(UiState.Idle)
    val state: StateFlow<UiState<List<Run>>> = _state

    fun loadForPipeline(pipelineId: String) {
        _state.value = UiState.Loading
        scope.launch {
            when (val res = repository.getRunsForPipeline(pipelineId)) {
                is Result.Failure -> _state.value = UiState.Error("Failed to load runs", res.exception)
                is Result.Success -> _state.value = if (res.value.isEmpty()) UiState.Empty() else UiState.Content(res.value)
            }
        }
    }

    fun update(run: Run) {
        scope.launch { repository.updateRun(run) }
    }

    fun observe(runId: String) = monitorRun?.observe(runId)
}


