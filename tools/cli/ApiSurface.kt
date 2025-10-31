package tools.cli

import aifactory.core.Result
import tools.ArtifactPaths
import tools.validators.ApiSurfaceValidator

object ApiSurfaceCli {
    fun check(): Result<Unit> {
        val outcome = ApiSurfaceValidator().check()
        val logName = ArtifactPaths.timestampedLogName("api-surface-check")
        val payload = when (outcome) {
            is aifactory.core.Result.Success ->
                """{"status":"passed","breakingChanges":${stringList(outcome.value.breakingChanges)}}"""
            is aifactory.core.Result.Failure ->
                """{"status":"failed","error":"${escape(outcome.exception.message ?: "unknown error")}"}"""
        }
        ArtifactPaths.writeLog(logName, payload)
        return when (outcome) {
            is aifactory.core.Result.Success -> Result.Success(Unit)
            is aifactory.core.Result.Failure -> Result.Failure(Exception("api-surface failed", outcome.exception))
        }
    }

    private fun stringList(values: List<String>): String =
        values.joinToString(prefix = "[", postfix = "]") { "\"${escape(it)}\"" }

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")
}


