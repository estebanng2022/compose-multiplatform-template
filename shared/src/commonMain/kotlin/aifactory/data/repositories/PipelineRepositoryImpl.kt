package aifactory.data.repositories

import aifactory.core.Result
import aifactory.core.Config
import aifactory.data.datasources.LocalFileDataSource
import aifactory.data.datasources.YamlDataSource
import aifactory.domain.models.Pipeline

class PipelineRepositoryImpl(
    private val fileDataSource: LocalFileDataSource,
    private val yamlDataSource: YamlDataSource
) : PipelineRepository {

    override suspend fun getPipelines(): Result<List<Pipeline>> {
        return yamlDataSource.loadFrom(Config.Paths.PIPELINES_FILE)
    }

    override suspend fun getPipeline(id: String): Result<Pipeline> {
        val all = getPipelines()
        return when (all) {
            is Result.Failure -> all
            is Result.Success -> {
                val pipeline = all.value.firstOrNull { it.id == id }
                if (pipeline == null) Result.Failure(NoSuchElementException("Pipeline $id not found"))
                else Result.Success(pipeline)
            }
        }
    }

    override suspend fun savePipeline(pipeline: Pipeline): Result<Unit> {
        // Read existing list
        val current = getPipelines()
        val updatedList = when (current) {
            is Result.Failure -> listOf(pipeline)
            is Result.Success -> current.value.filterNot { it.id == pipeline.id } + pipeline
        }
        // Serialize as YAML
        val yamlResult = try {
            // Reuse YAML data source to serialize by encoding via kotlinx.serialization
            // Kaml doesn't expose encode via Yaml.default without a serializer call here, so write minimal manual wrap
            val content = com.charleskorn.kaml.Yaml.default.encodeToString(
                kotlinx.serialization.builtins.ListSerializer(
                    kotlinx.serialization.serializer()
                ),
                updatedList
            )
            Result.Success(content)
        } catch (e: Exception) {
            Result.Failure(e)
        }
        return when (yamlResult) {
            is Result.Failure -> yamlResult
            is Result.Success -> fileDataSource.writeFile(Config.Paths.PIPELINES_FILE, yamlResult.value)
        }
    }
}
