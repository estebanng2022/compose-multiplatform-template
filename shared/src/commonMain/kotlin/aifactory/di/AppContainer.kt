package aifactory.di

import aifactory.core.FileIO
import aifactory.data.AiRepository
import aifactory.data.InMemoryAiRepository

/**
 * Simple DI container. Currently only exposes repository aggregator.
 */
interface AppContainer {
    val repository: AiRepository
}

class AppContainerImpl(
    fileIO: FileIO,
) : AppContainer {
    override val repository: AiRepository = InMemoryAiRepository(fileIO)
}
