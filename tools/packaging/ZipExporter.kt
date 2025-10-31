
package tools.packaging

import aifactory.core.Result
import tools.ArtifactPaths

class ZipExporter {
    fun export(paths: List<String>, outputZip: String): Result<String> {
        return try {
            val description = buildDescription(paths)
            val path = ArtifactPaths.writePlaceholderExport(outputZip, description)
            val normalized = normalize(path)
            logSuccess(normalized, paths)
            Result.Success(normalized)
        } catch (error: Exception) {
            logFailure(error, outputZip)
            Result.Failure(error)
        }
    }

    private fun buildDescription(paths: List<String>): String =
        if (paths.isEmpty()) {
            "ZIP placeholder without sources."
        } else {
            "ZIP placeholder containing: ${paths.joinToString()}"
        }

    private fun logSuccess(artifactPath: String, sources: List<String>) {
        val payload =
            """{"status":"passed","artifact":"$artifactPath","sources":${sources.joinToString(prefix = "[", postfix = "]") { "\"${escape(it)}\"" }}}"""
        val logName = ArtifactPaths.timestampedLogName("zip-exporter")
        ArtifactPaths.writeLog(logName, payload)
    }

    private fun logFailure(error: Throwable, outputZip: String) {
        val payload =
            """{"status":"failed","target":"$outputZip","error":"${escape(error.message ?: "unknown error")}"}"""
        val logName = ArtifactPaths.timestampedLogName("zip-exporter")
        ArtifactPaths.writeLog(logName, payload)
    }

    private fun normalize(path: java.nio.file.Path): String =
        path.toString().replace("\\", "/")

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")
}
