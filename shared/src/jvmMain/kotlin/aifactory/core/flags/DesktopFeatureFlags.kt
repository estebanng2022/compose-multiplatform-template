package aifactory.core.flags

class DesktopFeatureFlags : FeatureFlags {
    private fun boolProp(key: String, default: Boolean) =
        (System.getProperty(key) ?: System.getenv(key) ?: default.toString())
            .toString().lowercase() in listOf("1","true","yes","on")

    override val isWireframe = boolProp("FEATURE_WIREFRAME", false)
    override val useMockData = boolProp("USE_MOCK_DATA", false)
    override val showDebugOverlay = boolProp("SHOW_DEBUG_OVERLAY", false)
}
