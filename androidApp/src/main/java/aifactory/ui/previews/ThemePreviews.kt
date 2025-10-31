package aifactory.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import aifactory.ui.theme.Spacing

@Preview(name = "Light")
@Composable
fun ThemePreviewLight() {
    Text("AiFactory Theme Preview (Light)")
}

@Preview(name = "Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ThemePreviewDark() {
    Text("AiFactory Theme Preview (Dark)")
}


