package aifactory.di

import aifactory.core.JvmFileIO
import aifactory.data.AiRepository
import aifactory.data.InMemoryAiRepository

actual object Injector {
    actual fun provideRepository(): AiRepository {
        return InMemoryAiRepository(JvmFileIO())
    }
}

