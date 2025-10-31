package aifactory.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun AiFactoryTheme(
    darkTheme: Boolean = (LocalConfiguration.current.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == android.content.res.Configuration.UI_MODE_NIGHT_YES,
    content: @Composable () -> Unit
) {
    val themeVariant = remember { mutableStateOf(ThemeVariant.Premium) }
    val colorScheme = schemeFor(themeVariant.value, darkTheme)

    CompositionLocalProvider(
        LocalThemeVariant provides themeVariant,
        LocalSpacing provides Spacing(),
        LocalSizing provides Sizing()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

private fun schemeFor(variant: ThemeVariant, dark: Boolean): ColorScheme = when (variant) {
    ThemeVariant.Premium -> if (dark) darkPremium() else lightPremium()
    ThemeVariant.Minimal -> if (dark) darkMinimal() else lightMinimal()
    ThemeVariant.Animalita -> if (dark) darkAnimalita() else lightAnimalita()
}

private fun lightPremium(): ColorScheme = lightColorScheme(
    primary = Color(0xFF6366F1),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF312E81),
    secondary = Color(0xFFA855F7),
    background = Color(0xFFF8FAFC),
    surface = Color(0xFFFFFFFF),
)

private fun darkPremium(): ColorScheme = darkColorScheme(
    primary = Color(0xFF818CF8),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1E1B4B),
    secondary = Color(0xFF9333EA),
    background = Color(0xFF0B1020),
    surface = Color(0xFF0F172A),
)

private fun lightMinimal(): ColorScheme = lightColorScheme(
    primary = Color(0xFF111827),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE5E7EB),
    secondary = Color(0xFF9CA3AF),
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
)

private fun darkMinimal(): ColorScheme = darkColorScheme(
    primary = Color(0xFFE5E7EB),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF111827),
    secondary = Color(0xFF6B7280),
    background = Color(0xFF0B0B0B),
    surface = Color(0xFF101010),
)

private fun lightAnimalita(): ColorScheme = lightColorScheme(
    primary = Color(0xFFF97316),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEDD5),
    secondary = Color(0xFFFB923C),
    background = Color(0xFFFFFBF5),
    surface = Color(0xFFFFFFFF),
)

private fun darkAnimalita(): ColorScheme = darkColorScheme(
    primary = Color(0xFFFFA36D),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF7C2D12),
    secondary = Color(0xFFF97316),
    background = Color(0xFF1A120E),
    surface = Color(0xFF241A16),
)
