package aifactory.di

import aifactory.data.AiRepository

/**
 * A common entry point for dependency injection.
 */
expect object Injector {
    fun provideRepository(): AiRepository
}
