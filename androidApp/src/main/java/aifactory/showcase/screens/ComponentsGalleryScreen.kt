package aifactory.showcase.screens
import androidx.compose.foundation.layout.weight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import aifactory.ui.theme.LocalSpacing

@Composable
fun ComponentsGalleryScreen() {
    val spacing = LocalSpacing.current

    Surface(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.md, vertical = spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.lg),
        ) {
            item {
                Text(
                    text = "Componentes del Design System",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Referencia visual rÃ¡pida de botones, chips, tarjetas y estados.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = spacing.sm),
                )
            }

            item { ButtonsShowcase() }
            item { ChipsShowcase() }
            item { CardsShowcase() }
        }
    }
}

@Composable
private fun ButtonsShowcase() {
    val spacing = LocalSpacing.current
    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
        Text(
            text = "Botones",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
            Button(onClick = {}) { Text("Primario") }
            OutlinedButton(onClick = {}) { Text("Secundario") }
        }
        Divider(thickness = 1.dp, color = Color.White.copy(alpha = 0.1f))
    }
}

@Composable
private fun ChipsShowcase() {
    val spacing = LocalSpacing.current
    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
        Text(
            text = "Chips",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
            AssistChip(
                onClick = {},
                label = { Text("AI Agent") },
                colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF4F46E5), labelColor = Color.White),
            )
            AssistChip(
                onClick = {},
                label = { Text("Validator") },
                colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF0F172A), labelColor = Color(0xFF38BDF8)),
            )
        }
        Divider(thickness = 1.dp, color = Color.White.copy(alpha = 0.1f))
    }
}

@Composable
private fun CardsShowcase() {
    val spacing = LocalSpacing.current
    Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
        Text(
            text = "Tarjetas de Estado",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.sm)) {
            StatusCard(title = "Pipeline listo", description = "Todos los validadores han pasado.", color = Color(0xFF22C55E))
            StatusCard(title = "RevisiÃ³n requerida", description = "Hay 2 reglas con cambios pendientes.", color = Color(0xFFF97316))
        }
    }
}

@Composable
private fun StatusCard(title: String, description: String, color: Color) {
    val spacing = LocalSpacing.current
    Card(
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f)),
        modifier = Modifier.weight(1f),
    ) {
        Column(modifier = Modifier.padding(spacing.md)) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = color)
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = spacing.xs),
            )
        }
    }
}


