package tools.cli

import aifactory.ai.LocalAIManager
import aifactory.core.Result
import tools.ArtifactPaths

object AgentsCli {
    suspend fun scan(dir: String? = null): Result<Unit> {
        val manager = if (dir == null) LocalAIManager.default() else LocalAIManager(
            configSource = aifactory.ai.defaultAgentConfigSource(),
            agentsDirectory = dir,
        )
        val res = manager.loadAgents()
        val logName = ArtifactPaths.timestampedLogName("agents-scan")
        val payload = when (res) {
            is Result.Success -> {
                val arr = res.value.joinToString(prefix = "[", postfix = "]") { status ->
                    val tools = status.profile.tools.joinToString(prefix = "[", postfix = "]") { "\"${escape(it)}\"" }
                    """{"id":"${escape(status.profile.id)}","name":"${escape(status.profile.name)}","role":"${escape(status.profile.role)}","rules":"${escape(status.profile.rulesPath)}","tools":$tools,"online":${status.isOnline}}"""
                }
                """{"status":"passed","agents":$arr}"""
            }
            is Result.Failure -> """{"status":"failed","error":"${escape(res.exception.message ?: "unknown error")}"}"""
        }
        ArtifactPaths.writeLog(logName, payload)
        return when (res) {
            is Result.Success -> Result.Success(Unit)
            is Result.Failure -> Result.Failure(res.exception)
        }
    }

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")
}

