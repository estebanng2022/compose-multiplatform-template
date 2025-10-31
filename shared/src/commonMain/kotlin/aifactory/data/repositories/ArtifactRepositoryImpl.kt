package aifactory.data.repositories

import aifactory.core.Result
import aifactory.core.Config
import aifactory.data.datasources.LocalFileDataSource
import aifactory.data.datasources.JsonDataSource
import aifactory.domain.models.Artifact
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer

class ArtifactRepositoryImpl(
    private val fileDataSource: LocalFileDataSource,
    private val jsonDataSource: JsonDataSource
) : ArtifactRepository {

    override suspend fun saveArtifact(artifact: Artifact): Result<Unit> {
        val list = when (val current = getArtifacts(artifact.runId)) {
            is Result.Failure -> listOf(artifact)
            is Result.Success -> current.value + artifact
        }
        return saveForRun(artifact.runId, list)
    }

    override suspend fun getArtifacts(runId: String): Result<List<Artifact>> {
        val path = artifactsPath(runId)
        return if (!fileDataSource.exists(path)) Result.Success(emptyList())
        else jsonDataSource.parseFile(path)
    }

    private fun artifactsPath(runId: String): String = "${Config.Paths.ARTIFACTS_DIR}/$runId.json"

    private suspend fun saveForRun(runId: String, artifacts: List<Artifact>): Result<Unit> {
        val path = artifactsPath(runId)
        val json = try {
            val content = kotlinx.serialization.json.Json { encodeDefaults = true }
                .encodeToString(ListSerializer(serializer()), artifacts)
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