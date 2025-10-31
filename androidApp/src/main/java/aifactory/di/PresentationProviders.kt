package aifactory.di

import android.content.Context
import aifactory.core.AndroidFileIO
import aifactory.data.datasources.LocalFileDataSource
import aifactory.data.datasources.YamlDataSource
import aifactory.data.datasources.JsonDataSource
import aifactory.data.repositories.*
import aifactory.presentation.validation.PipelineValidator
import aifactory.presentation.viewmodels.*

object PresentationProviders {
    private fun fileDataSource(context: Context) = LocalFileDataSource(AndroidFileIO(context))
    private fun yamlDataSource(context: Context) = YamlDataSource(fileDataSource(context))
    private fun jsonDataSource(context: Context) = JsonDataSource(fileDataSource(context))

    fun agentsViewModel(context: Context): AgentsViewModel {
        val repo: AgentRepository = AgentRepositoryImpl(fileDataSource(context), yamlDataSource(context))
        return AgentsViewModel(repo)
    }

    fun pipelinesViewModel(context: Context): PipelinesViewModel {
        val repo: PipelineRepository = PipelineRepositoryImpl(fileDataSource(context), yamlDataSource(context))
        val validator = PipelineValidator()
        return PipelinesViewModel(repo, validator)
    }

    fun runsViewModel(context: Context): RunsViewModel {
        val repo: RunRepository = RunRepositoryImpl(fileDataSource(context), jsonDataSource(context))
        return RunsViewModel(repo)
    }
}
