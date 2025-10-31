package tools

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Centralizes artifact directories (logs, exports) and ensures they exist before use.
 */
object ArtifactPaths {
    private val timestampFormatter =
        DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").withZone(ZoneOffset.UTC)

    private val root: Path = Paths.get("artifacts")
    val logs: Path = root.resolve("logs")
    val exports: Path = root.resolve("exports")

    init {
        ensureBaseDirectories()
    }

    fun ensureBaseDirectories() {
        listOf(root, logs, exports).forEach { Files.createDirectories(it) }
    }

    fun writeLog(fileName: String, content: String): Path {
        ensureBaseDirectories()
        val target = logs.resolve(fileName)
        Files.write(
            target,
            content.toByteArray(StandardCharsets.UTF_8),
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE,
        )
        return target
    }

    fun timestampedLogName(prefix: String, extension: String = "json"): String {
        val timestamp = timestampFormatter.format(Instant.now())
        return "$prefix-$timestamp.$extension"
    }

    fun writePlaceholderExport(fileName: String, description: String): Path {
        ensureBaseDirectories()
        val target = exports.resolve(fileName)
        target.parent?.let { Files.createDirectories(it) }
        Files.write(
            target,
            description.toByteArray(StandardCharsets.UTF_8),
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE,
        )
        return target
    }
}
