package aifactory.ui.screens.detail

import aifactory.core.ServiceLocator
import aifactory.data.AiRepository
import aifactory.ui.screens.dashboard.DashboardItem
import aifactory.ui.widgets.states.AppState
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    private val repo: AiRepository = ServiceLocator.repository(application.applicationContext)
    val id: String = savedStateHandle["id"]!!

    private val _state = MutableStateFlow<AppState<DashboardItem>>(AppState.Loading)
    val state: StateFlow<AppState<DashboardItem>> = _state

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = AppState.Loading
            runCatching { repo.fetchDashboardItemById(id) }
                .onSuccess { item ->
                    _state.value = if (item != null) AppState.Content(item) else AppState.Empty("Item not found")
                }
                .onFailure { throwable ->
                    _state.value = AppState.Error("Failed to load item", throwable)
                }
        }
    }

    fun toggleFavorite(value: Boolean) {
        viewModelScope.launch {
            repo.setFavorite(id, value)
            if (_state.value is AppState.Content) {
                val currentItem = (_state.value as AppState.Content<DashboardItem>).data
                _state.value = AppState.Content(currentItem.copy(isFavorite = value))
            }
        }
    }

    fun sharePayload(): String {
        return if (state.value is AppState.Content) {
            val item = (state.value as AppState.Content<DashboardItem>).data
            "Check out this item: ${item.title} - ${item.subtitle}"
        } else {
            "Sharing from AiFactory App"
        }
    }
}
