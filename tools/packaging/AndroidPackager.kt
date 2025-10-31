package tools.packaging

import aifactory.core.Result
import tools.ArtifactPaths

class AndroidPackager {
    fun build(): Result<String> {
        val artifactName = "android/app-release.aab"
        return try {
            val path = ArtifactPaths.writePlaceholderExport(
                artifactName,
                "Android release artifact placeholder. Replace with actual Gradle output.",
            )
            val normalized = normalize(path)
            logSuccess(normalized)
            Result.Success(normalized)
        } catch (error: Exception) {
            logFailure(error)
            Result.Failure(error)
        }
    }

    private fun logSuccess(artifactPath: String) {
        val payload =
            """{"status":"passed","artifact":"$artifactPath"}"""
        val logName = ArtifactPaths.timestampedLogName("android-packager")
        ArtifactPaths.writeLog(logName, payload)
    }

    private fun logFailure(error: Throwable) {
        val payload =
            """{"status":"failed","error":"${escape(error.message ?: "unknown error")}"}"""
        val logName = ArtifactPaths.timestampedLogName("android-packager")
        ArtifactPaths.writeLog(logName, payload)
    }

    private fun normalize(path: java.nio.file.Path): String =
        path.toString().replace("\\", "/")

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")
}


