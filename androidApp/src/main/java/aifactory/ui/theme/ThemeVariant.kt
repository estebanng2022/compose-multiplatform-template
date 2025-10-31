package aifactory.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf

enum class ThemeVariant { Premium, Minimal, Animalita }

val LocalThemeVariant = staticCompositionLocalOf<MutableState<ThemeVariant>> {
    error("LocalThemeVariant not provided")
}

