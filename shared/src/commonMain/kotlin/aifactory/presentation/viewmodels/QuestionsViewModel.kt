package aifactory.presentation.viewmodels

import aifactory.core.Result
import aifactory.data.repositories.QuestionsRepository
import aifactory.domain.models.QuestionSet
import aifactory.presentation.state.QuestionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestionsViewModel(
    private val repository: QuestionsRepository,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _state = MutableStateFlow(QuestionsState())
    val state: StateFlow<QuestionsState> = _state

    fun load(projectId: String?) {
        _state.value = _state.value.copy(isLoading = true, error = null)
        scope.launch {
            val global = repository.loadGlobal()
            val final = when (global) {
                is Result.Failure -> global
                is Result.Success -> {
                    if (projectId == null) global else {
                        val project = repository.loadForProject(projectId)
                        when (project) {
                            is Result.Failure -> global
                            is Result.Success -> repository.mergeOverrides(global.value, project.value)
                        }
                    }
                }
            }
            when (final) {
                is Result.Failure -> _state.value = _state.value.copy(isLoading = false, error = final.exception.message)
                is Result.Success -> _state.value = _state.value.copy(isLoading = false, items = final.value.items, index = 0)
            }
        }
    }

    fun next() { _state.value = _state.value.copy(index = (_state.value.index + 1).coerceAtMost(_state.value.items.lastIndex)) }
    fun prev() { _state.value = _state.value.copy(index = (_state.value.index - 1).coerceAtLeast(0)) }
}

