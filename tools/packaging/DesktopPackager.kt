
package tools.packaging

import aifactory.core.Result
import tools.ArtifactPaths

class DesktopPackager {
    fun build(): Result<String> {
        val outputs = listOf(
            "desktop/windows/AiFactory.exe" to "Windows executable placeholder.",
            "desktop/macos/AiFactory.app" to "macOS bundle placeholder.",
            "desktop/linux/AiFactory.AppImage" to "Linux AppImage placeholder.",
        )

        return try {
            val created = outputs.map { (path, description) ->
                ArtifactPaths.writePlaceholderExport(path, description).let { normalize(it) }
            }
            logSuccess(created)
            Result.Success(normalize(ArtifactPaths.exports.resolve("desktop")))
        } catch (error: Exception) {
            logFailure(error)
            Result.Failure(error)
        }
    }

    private fun logSuccess(paths: List<String>) {
        val payload =
            """{"status":"passed","artifacts":${paths.joinToString(prefix = "[", postfix = "]") { "\"${escape(it)}\"" }}}"""
        val logName = ArtifactPaths.timestampedLogName("desktop-packager")
        ArtifactPaths.writeLog(logName, payload)
    }

    private fun logFailure(error: Throwable) {
        val payload =
            """{"status":"failed","error":"${escape(error.message ?: "unknown error")}"}"""
        val logName = ArtifactPaths.timestampedLogName("desktop-packager")
        ArtifactPaths.writeLog(logName, payload)
    }

    private fun normalize(path: java.nio.file.Path): String =
        path.toString().replace("\\", "/")

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")
}
