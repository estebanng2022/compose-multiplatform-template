package tools.cli

import aifactory.core.Result
import tools.ArtifactPaths
import tools.validators.AccessibilityValidator
import tools.validators.ApiSurfaceValidator
import tools.validators.SnapshotValidator
import tools.validators.TokenValidator
import aifactory.presentation.validation.ThemeValidator
import aifactory.core.JvmFileIO

object ValidateCli {
    fun run(): Result<Unit> {
        val entries = mutableListOf<String>()
        var failure: Throwable? = null

        val tasks = listOf(
            ValidatorTask(
                name = "accessibility",
                execute = { AccessibilityValidator().run() },
                onSuccess = {
                    """{"validator":"accessibility","status":"passed","passed":${it.passed},"issues":${stringList(it.issues)}}"""
                },
            ),
            ValidatorTask(
                name = "theme",
                execute = { validateThemeFromSeeds() },
                onSuccess = {
                    """{"validator":"theme","status":"passed"}"""
                },
            ),
            ValidatorTask(
                name = "tokens",
                execute = { TokenValidator().run() },
                onSuccess = {
                    """{"validator":"tokens","status":"passed","passed":${it.passed},"issues":${stringList(it.issues)}}"""
                },
            ),
            ValidatorTask(
                name = "snapshots-record",
                execute = { SnapshotValidator().record() },
                onSuccess = {
                    """{"validator":"snapshots-record","status":"passed","recorded":${it.recorded},"differences":${it.differences}}"""
                },
            ),
            ValidatorTask(
                name = "snapshots-verify",
                execute = { SnapshotValidator().verify() },
                onSuccess = {
                    """{"validator":"snapshots-verify","status":"passed","recorded":${it.recorded},"differences":${it.differences}}"""
                },
            ),
            ValidatorTask(
                name = "api-surface",
                execute = { ApiSurfaceValidator().check() },
                onSuccess = {
                    """{"validator":"api-surface","status":"passed","passed":${it.passed},"breakingChanges":${stringList(it.breakingChanges)}}"""
                },
            ),
        )

        for (task in tasks) {
            val outcome = runValidator(task)
            entries += outcome.entry
            if (outcome.failure != null) {
                failure = outcome.failure
                break
            }
        }

        val status = if (failure == null) "passed" else "failed"
        val payload =
            """{"status":"$status","validators":[${entries.joinToString(",")}]}"""
        val logName = ArtifactPaths.timestampedLogName("validate")
        ArtifactPaths.writeLog(logName, payload)

        return if (failure == null) {
            Result.Success(Unit)
        } else {
            Result.Failure(failure!!)
        }
    }

    private fun <T> runValidator(task: ValidatorTask<T>): ValidatorOutcome {
        val outcome = task.execute()
        return when (outcome) {
            is aifactory.core.Result.Success -> ValidatorOutcome(entry = task.onSuccess(outcome.value), failure = null)
            is aifactory.core.Result.Failure -> ValidatorOutcome(
                entry =
                    """{"validator":"${task.name}","status":"failed","error":"${escape(outcome.exception.message ?: "unknown error")}"}""",
                failure = Exception("${task.name} validator failed", outcome.exception),
            )
        }
    }

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")

    private fun stringList(values: List<String>): String =
        values.joinToString(prefix = "[", postfix = "]") { "\"${escape(it)}\"" }

    private data class ValidatorOutcome(val entry: String, val failure: Throwable?)

    private data class ValidatorTask<T>(
        val name: String,
        val execute: () -> aifactory.core.Result<T>,
        val onSuccess: (T) -> String,
    )

    private fun validateThemeFromSeeds(): aifactory.core.Result<Unit> {
        val fallback = "shared/src/commonMain/kotlin/aifactory/ui/themes/seeds/premium/theme.yaml"
        val io = JvmFileIO()
        val content = kotlinx.coroutines.runBlocking { io.read(fallback) }
        return when (content) {
            is aifactory.core.Result.Failure -> content
            is aifactory.core.Result.Success -> {
                val report = ThemeValidator.validateYaml(content.value)
                if (report.passed) aifactory.core.Result.Success(Unit)
                else aifactory.core.Result.Failure(IllegalStateException(report.issues.joinToString()))
            }
        }
    }
}
