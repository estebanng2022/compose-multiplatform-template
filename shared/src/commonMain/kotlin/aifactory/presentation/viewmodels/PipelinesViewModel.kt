package aifactory.presentation.viewmodels

import aifactory.core.Result
import aifactory.data.repositories.PipelineRepository
import aifactory.domain.models.Pipeline
import aifactory.domain.usecases.ExecutePipeline
import aifactory.presentation.state.UiState
import aifactory.presentation.validation.PipelineValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PipelinesViewModel(
    private val repository: PipelineRepository,
    private val validator: PipelineValidator,
    private val executePipeline: ExecutePipeline? = null
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _state = MutableStateFlow<UiState<List<Pipeline>>>(UiState.Idle)
    val state: StateFlow<UiState<List<Pipeline>>> = _state

    fun refresh() {
        _state.value = UiState.Loading
        scope.launch {
            when (val res = repository.getPipelines()) {
                is Result.Failure -> _state.value = UiState.Error("Failed to load pipelines", res.exception)
                is Result.Success -> _state.value = if (res.value.isEmpty()) UiState.Empty() else UiState.Content(res.value)
            }
        }
    }

    fun save(pipeline: Pipeline) {
        scope.launch {
            when (val v = validator.validate(pipeline)) {
                is Result.Failure -> _state.value = UiState.Error("Invalid pipeline", v.exception)
                is Result.Success -> {
                    when (val r = repository.savePipeline(pipeline)) {
                        is Result.Failure -> _state.value = UiState.Error("Failed to save pipeline", r.exception)
                        is Result.Success -> refresh()
                    }
                }
            }
        }
    }

    fun start(pipeline: Pipeline) {
        val usecase = executePipeline ?: return
        _state.value = UiState.Loading
        scope.launch {
            when (val res = usecase(pipeline)) {
                is Result.Failure -> _state.value = UiState.Error("Failed to start pipeline", res.exception)
                is Result.Success -> refresh()
            }
        }
    }
}


