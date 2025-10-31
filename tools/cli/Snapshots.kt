package tools.cli

import aifactory.core.Result
import tools.ArtifactPaths
import tools.validators.SnapshotValidator

object SnapshotsCli {
    fun record(): Result<Unit> {
        val action = "record"
        val validator = SnapshotValidator()
        val outcome = validator.record()
        return logAndWrap(action, outcome)
    }

    fun verify(): Result<Unit> {
        val action = "verify"
        val validator = SnapshotValidator()
        val outcome = validator.verify()
        return logAndWrap(action, outcome)
    }

    private fun logAndWrap(
        action: String,
        result: aifactory.core.Result<SnapshotValidator.Report>,
    ): Result<Unit> {
        val logName = ArtifactPaths.timestampedLogName("snapshots-$action")
        val logPayload = when (result) {
            is aifactory.core.Result.Success ->
                """{"action":"$action","status":"passed","recorded":${result.value.recorded},"differences":${result.value.differences}}"""
            is aifactory.core.Result.Failure ->
                """{"action":"$action","status":"failed","error":"${escape(result.exception.message ?: "unknown error")}"}"""
        }
        ArtifactPaths.writeLog(logName, logPayload)
        return when (result) {
            is aifactory.core.Result.Success -> Result.Success(Unit)
            is aifactory.core.Result.Failure -> Result.Failure(Exception("$action failed", result.exception))
        }
    }

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")
}


