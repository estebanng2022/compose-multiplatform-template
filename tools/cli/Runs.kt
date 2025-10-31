package tools.cli

import aifactory.ai.AgentExecutor
import aifactory.ai.LocalAIManager
import aifactory.ai.RuleEngine
import aifactory.core.JvmFileIO
import aifactory.core.Result
import aifactory.data.datasources.JsonDataSource
import aifactory.data.datasources.LocalFileDataSource
import aifactory.data.datasources.YamlDataSource
import aifactory.data.repositories.PipelineRepository
import aifactory.data.repositories.PipelineRepositoryImpl
import aifactory.data.repositories.RunRepository
import aifactory.data.repositories.RunRepositoryImpl
import aifactory.domain.models.Run
import aifactory.domain.models.RunStatus
import kotlinx.datetime.Clock
import tools.ArtifactPaths

object RunsCli {
    private fun files() = LocalFileDataSource(JvmFileIO())
    private fun yaml() = YamlDataSource(files())
    private fun json() = JsonDataSource(files())
    private fun pipelines(): PipelineRepository = PipelineRepositoryImpl(files(), yaml())
    private fun runs(): RunRepository = RunRepositoryImpl(files(), json())

    suspend fun start(pipelineId: String): Result<String> {
        val pipeRes = pipelines().getPipeline(pipelineId)
        if (pipeRes is Result.Failure) return Result.Failure(pipeRes.exception)
        val pipeline = (pipeRes as Result.Success).value

        val manager = LocalAIManager.default()
        val engine = RuleEngine(manager, AgentExecutor())

        var run = Run(
            pipelineId = pipeline.id,
            status = RunStatus.RUNNING,
            logs = mutableListOf("Run started for pipeline ${pipeline.name}"),
        )

        val createRes = runs().createRun(run)
        if (createRes is Result.Failure) return Result.Failure(createRes.exception)
        val runId = (createRes as Result.Success).value

        val sim = engine.simulatePipeline(pipeline)
        run = when (sim) {
            is Result.Success -> run.copy(
                status = RunStatus.SUCCESS,
                logs = (run.logs + sim.value.steps.map { "${it.stepIndex + 1}. ${it.task.name}: ${it.status} - ${it.output}" })
                    .toMutableList(),
            )
            is Result.Failure -> run.copy(
                status = RunStatus.FAILURE,
                logs = (run.logs + listOf(sim.exception.message ?: "Pipeline execution failed"))
                    .toMutableList(),
            )
        }

        val update = runs().updateRun(run)
        val logName = ArtifactPaths.timestampedLogName("run-start")
        val payload = when (update) {
            is Result.Success -> """{"status":"passed","runId":"$runId"}"""
            is Result.Failure -> """{"status":"failed","error":"${escape(update.exception.message ?: "unknown error")}"}"""
        }
        ArtifactPaths.writeLog(logName, payload)
        return when (update) {
            is Result.Success -> Result.Success(runId)
            is Result.Failure -> Result.Failure(update.exception)
        }
    }

    suspend fun logs(runId: String): Result<Unit> {
        val r = runs().getRun(runId)
        val logName = ArtifactPaths.timestampedLogName("run-logs")
        val payload = when (r) {
            is Result.Success -> {
                val logsArr = r.value.logs.joinToString(prefix = "[", postfix = "]") { "\"${escape(it)}\"" }
                """{"status":"passed","runId":"$runId","logs":$logsArr,"statusText":"${r.value.status}"}"""
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

