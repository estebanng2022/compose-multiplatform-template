package aifactory.core

import aifactory.data.AiRepository
import aifactory.data.InMemoryAiRepository
import android.content.Context

/**
 * Android-specific implementation for providing dependencies.
 */
object AndroidServiceLocator {
    // Note: Caching can be added here if needed, but for now, we create new instances.
    fun provideRepository(context: Context): AiRepository {
        return InMemoryAiRepository(AndroidFileIO(context))
    }
}
