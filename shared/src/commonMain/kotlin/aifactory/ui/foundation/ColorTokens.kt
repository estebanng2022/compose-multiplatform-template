package aifactory.ui.foundation

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object AiColors {
    val primary = Color(0xFF8A72D2)
    val onPrimary = Color(0xFFFFFFFF)
    val secondary = Color(0xFF625B71)
    val onSecondary = Color(0xFFFFFFFF)

    // Light Theme
    val backgroundLight = Color(0xFFF9F9FF)
    val surfaceLight = Color(0xFFFFFFFF)
    val onSurfaceLight = Color(0xFF1B1B1F)
    val surfaceVariantLight = Color(0xFFE7E0EC)
    val onSurfaceVariantLight = Color(0xFF49454F)
    val cardBorderLight = Color(0xFFEAEAEA) // Subtle border for the light theme

    // Dark Theme
    val backgroundDark = Color(0xFF121318)
    val surfaceDark = Color(0xFF1A1B20)
    val onSurfaceDark = Color(0xFFE3E3E6)
    val surfaceVariantDark = Color(0xFF2E2F33)
    val onSurfaceVariantDark = Color(0xFF919296)
}

val LightColorScheme = lightColorScheme(
    primary = AiColors.primary,
    onPrimary = AiColors.onPrimary,
    secondary = AiColors.secondary,
    onSecondary = AiColors.onSecondary,
    background = AiColors.backgroundLight,
    surface = AiColors.surfaceLight,
    onSurface = AiColors.onSurfaceLight,
    surfaceVariant = AiColors.surfaceVariantLight,
    onSurfaceVariant = AiColors.onSurfaceVariantLight,
    outlineVariant = AiColors.cardBorderLight // Mapping the border color
)

val DarkColorScheme = darkColorScheme(
    primary = AiColors.primary,
    onPrimary = AiColors.onPrimary,
    secondary = AiColors.secondary,
    onSecondary = AiColors.onSecondary,
    background = AiColors.backgroundDark,
    surface = AiColors.surfaceDark,
    onSurface = AiColors.onSurfaceDark,
    surfaceVariant = AiColors.surfaceVariantDark,
    onSurfaceVariant = AiColors.onSurfaceVariantDark,
    // In the dark theme, the border is the same as the card background for a borderless look
    outlineVariant = AiColors.surfaceDark 
)
