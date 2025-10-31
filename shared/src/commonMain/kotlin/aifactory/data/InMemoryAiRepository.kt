package aifactory.data

import aifactory.core.FileIO
import aifactory.data.datasources.JsonDataSource
import aifactory.data.datasources.LocalFileDataSource
import aifactory.data.datasources.YamlDataSource
import aifactory.data.repositories.*

/**
 * Simple repository aggregator backed by local file storage.
 */
class InMemoryAiRepository(
    fileIO: FileIO,
) : AiRepository {
    private val files = LocalFileDataSource(fileIO)
    private val json = JsonDataSource(files)
    private val yaml = YamlDataSource(files)

    override val agents: AgentRepository = AgentRepositoryImpl(files, yaml)
    override val pipelines: PipelineRepository = PipelineRepositoryImpl(files, yaml)
    override val runs: RunRepository = RunRepositoryImpl(files, json)
    override val artifacts: ArtifactRepository = ArtifactRepositoryImpl(files, json)
    override val rules: RuleSetRepository = RuleSetRepositoryImpl(files)
}

