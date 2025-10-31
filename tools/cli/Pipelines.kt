package tools.cli

import aifactory.core.Result
import aifactory.core.JvmFileIO
import aifactory.data.datasources.LocalFileDataSource
import aifactory.data.datasources.YamlDataSource
import aifactory.data.repositories.PipelineRepository
import aifactory.data.repositories.PipelineRepositoryImpl
import tools.ArtifactPaths

object PipelinesCli {
    private fun repo(): PipelineRepository {
        val file = LocalFileDataSource(JvmFileIO())
        val yaml = YamlDataSource(file)
        return PipelineRepositoryImpl(file, yaml)
    }

    suspend fun list(): Result<Unit> {
        val r = repo().getPipelines()
        val logName = ArtifactPaths.timestampedLogName("pipelines-list")
        val payload = when (r) {
            is Result.Success -> {
                val arr = r.value.joinToString(prefix = "[", postfix = "]") { p ->
                    val tasks = p.tasks.joinToString(prefix = "[", postfix = "]") { t ->
                        val params = t.params.entries.joinToString(prefix = "{", postfix = "}") { (k, v) ->
                            "\"${escape(k)}\":\"${escape(v)}\""
                        }
                        """{"id":"${escape(t.id)}","name":"${escape(t.name)}","action":"${escape(t.action)}","params":$params}"""
                    }
                    """{"id":"${escape(p.id)}","name":"${escape(p.name)}","tasks":$tasks}"""
                }
                """{"status":"passed","pipelines":$arr}"""
            }
            is Result.Failure -> """{"status":"failed","error":"${escape(r.exception.message ?: "unknown error")}"}"""
        }
        ArtifactPaths.writeLog(logName, payload)
        return when (r) {
            is Result.Success -> Result.Success(Unit)
            is Result.Failure -> Result.Failure(r.exception)
        }
    }

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")
}

