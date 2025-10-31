package aifactory.feature.projects.domain.repo
import aifactory.feature.projects.domain.model.Project
interface ProjectService {
    suspend fun list(): Result<List<Project>>
    suspend fun create(name: String): Result<Project>
}
