package aifactory.data.repositories

import aifactory.core.Result
import aifactory.domain.models.Run

interface RunRepository {
    suspend fun createRun(run: Run): Result<String>
    suspend fun getRun(id: String): Result<Run>
    suspend fun updateRun(run: Run): Result<Unit>
    suspend fun getRunsForPipeline(pipelineId: String): Result<List<Run>>
}
