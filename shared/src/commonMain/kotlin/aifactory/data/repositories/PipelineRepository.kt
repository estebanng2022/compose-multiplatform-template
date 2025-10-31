package aifactory.data.repositories

import aifactory.core.Result
import aifactory.domain.models.Pipeline

interface PipelineRepository {
    suspend fun getPipelines(): Result<List<Pipeline>>
    suspend fun getPipeline(id: String): Result<Pipeline>
    suspend fun savePipeline(pipeline: Pipeline): Result<Unit>
}
