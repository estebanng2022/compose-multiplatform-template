package aifactory.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class Sizing(
    val iconSm: androidx.compose.ui.unit.Dp = 16.dp,
    val iconMd: androidx.compose.ui.unit.Dp = 24.dp,
    val iconLg: androidx.compose.ui.unit.Dp = 32.dp
)

val LocalSizing = staticCompositionLocalOf { Sizing() }


