package aifactory.ui.widgets.states

import androidx.compose.runtime.Composable

/**
 * A sealed interface representing the possible states of a screen or component
 * that loads data.
 */
sealed interface AppState<out T> {
    data object Loading : AppState<Nothing>
    data class Empty(val message: String = "No data") : AppState<Nothing>
    data class Error(val message: String, val throwable: Throwable? = null) : AppState<Nothing>
    data class Content<T>(val data: T) : AppState<T>
}

/**
 * A composable helper that renders the UI based on the given AppState.
 */
@Composable
fun <T> RenderAppState(
    state: AppState<T>,
    onRetry: (() -> Unit)? = null,
    content: @Composable (T) -> Unit
) {
    when (state) {
        is AppState.Loading -> LoadingState()
        is AppState.Empty -> EmptyState(message = state.message)
        is AppState.Error -> ErrorState(message = state.message, onRetry = onRetry)
        is AppState.Content -> content(state.data)
    }
}
