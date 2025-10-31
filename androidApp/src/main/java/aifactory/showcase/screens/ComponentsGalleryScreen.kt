package aifactory.showcase.screens
import androidx.compose.foundation.layout.*

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
import aifactory.ui.foundation.Spacing

@Composable
fun ComponentsGalleryScreen() {
    val spacing = Spacing

    Surface(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium, vertical = spacing.medium),
            verticalArrangement = Arrangement.spacedBy(spacing.large),
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
                    modifier = Modifier.padding(top = spacing.small),
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
    val spacing = Spacing
    Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
        Text(
            text = "Botones",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
            Button(onClick = {}) { Text("Primario") }
            OutlinedButton(onClick = {}) { Text("Secundario") }
        }
        Divider(thickness = 1.dp, color = Color.White.copy(alpha = 0.1f))
    }
}

@Composable
private fun ChipsShowcase() {
    val spacing = Spacing
    Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
        Text(
            text = "Chips",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
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
    val spacing = Spacing
    Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
        Text(
            text = "Tarjetas de Estado",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
            StatusCard(title = "Pipeline listo", description = "Todos los validadores han pasado.", color = Color(0xFF22C55E), modifier = Modifier.weight(1f))
            StatusCard(title = "RevisiÃ³n requerida", description = "Hay 2 reglas con cambios pendientes.", color = Color(0xFFF97316))
        }
    }
}

@Composable
private fun StatusCard(title: String, description: String, color: Color, modifier: Modifier = Modifier) {
    val spacing = Spacing
    Card(colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f)), modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(spacing.medium)) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = color)
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = spacing.xSmall),
            )
        }
    }
}



