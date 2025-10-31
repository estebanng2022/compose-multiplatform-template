package aifactory.data.repositories

import aifactory.core.Result
import aifactory.core.Config
import aifactory.data.datasources.LocalFileDataSource
import aifactory.data.datasources.JsonDataSource
import aifactory.domain.models.Run
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer

class RunRepositoryImpl(
    private val fileDataSource: LocalFileDataSource,
    private val jsonDataSource: JsonDataSource
) : RunRepository {

    override suspend fun createRun(run: Run): Result<String> {
        val listResult = loadAllRuns()
        val updated = when (listResult) {
            is Result.Failure -> listOf(run)
            is Result.Success -> listResult.value + run
        }
        return saveAllRuns(updated).let { res ->
            when (res) {
                is Result.Failure -> res
                is Result.Success -> Result.Success(run.id)
            }
        }
    }

    override suspend fun getRun(id: String): Result<Run> {
        return when (val all = loadAllRuns()) {
            is Result.Failure -> all
            is Result.Success -> {
                val found = all.value.firstOrNull { it.id == id }
                if (found == null) Result.Failure(NoSuchElementException("Run $id not found"))
                else Result.Success(found)
            }
        }
    }

    override suspend fun updateRun(run: Run): Result<Unit> {
        val listResult = loadAllRuns()
        val updated = when (listResult) {
            is Result.Failure -> listOf(run)
            is Result.Success -> listResult.value.map { if (it.id == run.id) run else it }
        }
        return saveAllRuns(updated)
    }

    override suspend fun getRunsForPipeline(pipelineId: String): Result<List<Run>> {
        return when (val all = loadAllRuns()) {
            is Result.Failure -> all
            is Result.Success -> Result.Success(all.value.filter { it.pipelineId == pipelineId })
        }
    }

    private suspend fun loadAllRuns(): Result<List<Run>> {
        val path = "${Config.Paths.RUNS_DIR}/runs.json"
        return if (!fileDataSource.exists(path)) {
            Result.Success(emptyList())
        } else {
            jsonDataSource.parseFile(path)
        }
    }

    private suspend fun saveAllRuns(runs: List<Run>): Result<Unit> {
        val path = "${Config.Paths.RUNS_DIR}/runs.json"
        val json = try {
            val content = kotlinx.serialization.json.Json { encodeDefaults = true }
                .encodeToString(ListSerializer(serializer()), runs)
            Result.Success(content)
        } catch (e: Exception) {
            Result.Failure(e)
        }
        return when (json) {
            is Result.Failure -> json
            is Result.Success -> fileDataSource.writeFile(path, json.value)
        }
    }
}