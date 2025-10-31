package aifactory.data

import aifactory.core.flags.LocalFeatureFlags
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

// Assuming RealRepository and MockRepository exist and implement AiRepository
// For now, we will use ServiceLocator as the "real" one.

@Composable
fun rememberRepository(): AiRepository {
    val flags = LocalFeatureFlags.current
    val context = LocalContext.current
    return remember(flags.useMockData) {
        if (flags.useMockData) {
            // Return a mock repository instance
            // For now, we can use the existing InMemoryAiRepository as it's a mock.
            aifactory.core.ServiceLocator.repository(context)
        } else {
            // Return the real repository instance
            // Since we don't have a real one, we return the same for now.
            aifactory.core.ServiceLocator.repository(context)
        }
    }
}
