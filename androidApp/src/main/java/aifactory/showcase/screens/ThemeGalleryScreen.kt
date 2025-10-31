package aifactory.showcase.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import aifactory.ui.theme.LocalSpacing
import aifactory.ui.theme.LocalThemeVariant
import aifactory.ui.theme.ThemeVariant

private data class ThemeSample(
    val name: String,
    val palette: List<Color>,
    val description: String,
)

@Composable
fun ThemeGalleryScreen() {
    val spacing = LocalSpacing.current
    val themes = remember {
        listOf(
            ThemeSample(
                name = "Premium",
                palette = listOf(Color(0xFF1E1B4B), Color(0xFF6366F1), Color(0xFFA855F7)),
                description = "Gradients, alto contraste y acentos violetas para productos premium.",
            ),
            ThemeSample(
                name = "Minimal",
                palette = listOf(Color(0xFFF5F5F5), Color(0xFF9CA3AF), Color(0xFF111827)),
                description = "Espacios generosos, tipografías neutras y foco en la jerarquía visual.",
            ),
            ThemeSample(
                name = "Animalita",
                palette = listOf(Color(0xFFFFEDD5), Color(0xFFFB923C), Color(0xFFF97316)),
                description = "Colores cálidos y orgánicos para experiencias más lúdicas.",
            ),
        )
    }

    Surface(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.md, vertical = spacing.md),
            verticalArrangement = Arrangement.spacedBy(spacing.md),
        ) {
            item {
                Text(
                    text = "Showcase de Themes",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Visualiza los estilos preconfigurados para proyectos premium.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = spacing.sm),
                )
            }
            items(themes) { theme ->
                ThemePreviewCard(theme = theme)
            }
        }
    }
}

@Composable
private fun ThemePreviewCard(theme: ThemeSample) {
    val spacing = LocalSpacing.current
    val themeVariant = LocalThemeVariant.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0x0DFFFFFF)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.md),
        ) {
            Text(
                text = theme.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = theme.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = spacing.sm),
            )
            TextButton(onClick = {
                val variant = when (theme.name.lowercase()) {
                    "premium" -> ThemeVariant.Premium
                    "minimal" -> ThemeVariant.Minimal
                    else -> ThemeVariant.Animalita
                }
                themeVariant.value = variant
            }) {
                Text("Aplicar este theme")
            }
            Row(
                modifier = Modifier
                    .padding(top = spacing.md)
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.spacedBy(spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                theme.palette.forEach { color ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(color.copy(alpha = 0.85f), color),
                                ),
                            ),
                    ) {}
                }
            }
        }
    }
}
