package aifactory.data.repositories

import aifactory.core.Result
import aifactory.domain.models.Artifact

interface ArtifactRepository {
    suspend fun saveArtifact(artifact: Artifact): Result<Unit>
    suspend fun getArtifacts(runId: String): Result<List<Artifact>>
}
