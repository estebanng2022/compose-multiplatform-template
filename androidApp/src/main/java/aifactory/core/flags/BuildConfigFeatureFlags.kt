package aifactory.core.flags

import com.myapplication.BuildConfig

class BuildConfigFeatureFlags : FeatureFlags {
    override val isWireframe = BuildConfig.FEATURE_WIREFRAME
    override val useMockData = BuildConfig.USE_MOCK_DATA
    override val showDebugOverlay = BuildConfig.SHOW_DEBUG_OVERLAY
}
