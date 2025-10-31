package tools.cli

import aifactory.core.Result
import tools.ArtifactPaths
import tools.validators.AccessibilityValidator

object A11yCli {
    fun check(): Result<Unit> {
        val validator = AccessibilityValidator()
        val outcome = validator.run()
        val logName = ArtifactPaths.timestampedLogName("a11y-check")
        val payload = when (outcome) {
            is aifactory.core.Result.Success ->
                """{"status":"passed","issues":${stringList(outcome.value.issues)}}"""
            is aifactory.core.Result.Failure ->
                """{"status":"failed","error":"${escape(outcome.exception.message ?: "unknown error")}"}"""
        }
        ArtifactPaths.writeLog(logName, payload)
        return when (outcome) {
            is aifactory.core.Result.Success -> Result.Success(Unit)
            is aifactory.core.Result.Failure -> Result.Failure(Exception("a11y failed", outcome.exception))
        }
    }

    private fun stringList(values: List<String>): String =
        values.joinToString(prefix = "[", postfix = "]") { "\"${escape(it)}\"" }

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")
}


