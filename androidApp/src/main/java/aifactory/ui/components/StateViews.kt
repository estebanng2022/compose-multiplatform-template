package aifactory.ui.components

import aifactory.presentation.state.UiState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> RenderUiState(
    state: UiState<T>,
    render: @Composable (T) -> Unit
) {
    when (state) {
        is UiState.Idle -> {}
        is UiState.Loading -> Box(Modifier.fillMaxSize()) { CircularProgressIndicator() }
        is UiState.Empty -> Box(Modifier.fillMaxSize()) { Text(state.reason ?: "No content") }
        is UiState.Error -> Box(Modifier.fillMaxSize()) { Text(state.message) }
        is UiState.Content -> render(state.data)
    }
}


