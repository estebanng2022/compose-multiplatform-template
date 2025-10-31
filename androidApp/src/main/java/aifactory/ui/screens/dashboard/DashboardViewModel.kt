package aifactory.ui.screens.dashboard

import aifactory.core.ServiceLocator
import aifactory.data.AiRepository
import aifactory.ui.widgets.states.AppState
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AiRepository = ServiceLocator.repository(application.applicationContext)

    private val _state = MutableStateFlow<AppState<List<DashboardItem>>>(AppState.Loading)
    val state: StateFlow<AppState<List<DashboardItem>>> = _state

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    private val _canLoadMore = MutableStateFlow(true)
    val canLoadMore: StateFlow<Boolean> = _canLoadMore

    private var nextCursor: String? = null

    init {
        dispatch(DashboardAction.Refresh)
    }

    fun dispatch(action: DashboardAction) {
        when (action) {
            is DashboardAction.Refresh -> refresh()
            is DashboardAction.LoadMore -> loadMore()
            is DashboardAction.ToggleFavorite -> toggleFavorite(action.id, action.value)
            is DashboardAction.Open -> open(action.id)
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.value = AppState.Loading
            nextCursor = null
            _canLoadMore.value = true
            runCatching { repo.fetchDashboardPage(cursor = null) }
                .onSuccess { page ->
                    nextCursor = page.nextCursor
                    _canLoadMore.value = page.nextCursor != null
                    _state.value = if (page.items.isEmpty()) AppState.Empty("No dashboard items") else AppState.Content(page.items)
                }
                .onFailure { throwable ->
                    _state.value = AppState.Error("Failed to load dashboard", throwable)
                }
        }
    }

    private fun loadMore() {
        if (!_canLoadMore.value || _isLoadingMore.value) return

        viewModelScope.launch {
            _isLoadingMore.value = true
            runCatching { repo.fetchDashboardPage(cursor = nextCursor) }
                .onSuccess { page ->
                    nextCursor = page.nextCursor
                    _canLoadMore.value = page.nextCursor != null
                    // The repository now returns the full list, so we just replace it.
                    _state.value = AppState.Content(page.items)
                }
                .onFailure { /* Optionally handle error state for load more */ }
            _isLoadingMore.value = false
        }
    }

    private fun toggleFavorite(id: String, value: Boolean) {
        viewModelScope.launch {
            repo.setFavorite(id, value)
            if (_state.value is AppState.Content) {
                val currentItems = (_state.value as AppState.Content<List<DashboardItem>>).data.map {
                    if (it.id == id) it.copy(isFavorite = value) else it
                }
                _state.value = AppState.Content(currentItems)
            }
        }
    }

    private fun open(id: String) {
        // In a real app, this would likely emit a one-time event to trigger navigation
        // For now, we just log it.
        aifactory.core.Logx.d(msg = "Open item with id: $id")
    }
}
