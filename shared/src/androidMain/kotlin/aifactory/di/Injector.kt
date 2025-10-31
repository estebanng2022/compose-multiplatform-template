package aifactory.di

import aifactory.core.AndroidFileIO
import aifactory.data.AiRepository
import aifactory.data.InMemoryAiRepository
import aifactory.platform.AppContext

actual object Injector {
    actual fun provideRepository(): AiRepository {
        return InMemoryAiRepository(AndroidFileIO(AppContext.context))
    }
}

