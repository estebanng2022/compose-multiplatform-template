package aifactory.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ThemePreviewPanel(primary: Color, secondary: Color, background: Color, surface: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Theme Preview", style = MaterialTheme.typography.titleSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Swatch("Primary", primary)
            Swatch("Secondary", secondary)
            Swatch("Background", background)
            Swatch("Surface", surface)
        }
    }
}

@Composable
private fun Swatch(label: String, color: Color) {
    Card(colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.9f))) {
        Column(Modifier.fillMaxWidth().height(64.dp).background(color).padding(8.dp)) {
            val textColor = if ((color.red*0.299 + color.green*0.587 + color.blue*0.114) > 0.5) Color.Black else Color.White
            Text(label, color = textColor)
        }
    }
}

