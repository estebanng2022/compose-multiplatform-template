package aifactory.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import aifactory.ui.widgets.RenderUiState
import aifactory.presentation.state.UiState

@Preview
@Composable
fun StateView_Loading_Preview() {
    Surface { RenderUiState(state = UiState.Loading) { Text("Loaded") } }
}

@Preview
@Composable
fun StateView_Empty_Preview() {
    Surface { RenderUiState(state = UiState.Empty("Nothing")) { Text("Loaded") } }
}

@Preview
@Composable
fun StateView_Error_Preview() {
    Surface { RenderUiState(state = UiState.Error("Oops")) { Text("Loaded") } }
}

@Preview
@Composable
fun StateView_Content_Preview() {
    Surface { RenderUiState(state = UiState.Content("Hello")) { Text(it, Modifier.padding(8.dp)) } }
}


