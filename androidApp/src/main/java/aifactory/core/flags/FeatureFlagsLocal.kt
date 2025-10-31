package aifactory.core.flags

import androidx.compose.runtime.staticCompositionLocalOf

val LocalFeatureFlags = staticCompositionLocalOf<FeatureFlags> {
    error("FeatureFlags no inicializados")
}
