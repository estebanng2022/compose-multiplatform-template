package aifactory.desktop

import aifactory.core.flags.FeatureFlags

private fun sysBool(name: String, default: Boolean) =
    (System.getProperty(name) ?: System.getenv(name) ?: default.toString())
        .trim().lowercase() in setOf("1","true","yes","y")

class DesktopFeatureFlags : FeatureFlags {
    override val isWireframe = sysBool("FEATURE_WIREFRAME", true)
    override val useMockData = sysBool("USE_MOCK_DATA", true)
    override val showDebugOverlay = sysBool("SHOW_DEBUG_OVERLAY", true)
}
