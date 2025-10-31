package aifactory.data

import aifactory.data.repositories.AgentRepository
import aifactory.data.repositories.ArtifactRepository
import aifactory.data.repositories.PipelineRepository
import aifactory.data.repositories.RuleSetRepository
import aifactory.data.repositories.RunRepository

/**
 * Aggregates all repositories used by the app so DI can expose a single entrypoint.
 */
interface AiRepository {
    val agents: AgentRepository
    val pipelines: PipelineRepository
    val runs: RunRepository
    val artifacts: ArtifactRepository
    val rules: RuleSetRepository
}

