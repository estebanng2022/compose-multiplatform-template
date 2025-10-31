package aifactory.ai

import aifactory.core.Result
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors
import kotlin.io.path.extension
import kotlin.io.path.isRegularFile
import kotlin.io.path.name

actual fun defaultAgentConfigSource(): AgentConfigSource = FileAgentConfigSource()

class FileAgentConfigSource(
    private val workingDirectory: Path = Paths.get("").toAbsolutePath(),
    private val charset: Charset = Charsets.UTF_8,
) : AgentConfigSource {

    override suspend fun listAgentConfigs(directory: String): Result<List<AgentConfigDocument>> {
        val dirPath = workingDirectory.resolve(directory).normalize()
        if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
            return Result.Success(emptyList())
        }

        return try {
            val documents = Files.list(dirPath).use { stream ->
                stream
                    .filter { it.isRegularFile() && it.extension.equals("yaml", ignoreCase = true) }
                    .sorted()
                    .map { path -> path.toAgentConfigDocument(charset) }
                    .collect(Collectors.toList())
            }
            Result.Success(documents)
        } catch (io: IOException) {
            Result.Failure(io)
        }
    }

    private fun Path.toAgentConfigDocument(charset: Charset): AgentConfigDocument {
        val content = Files.readString(this, charset)
        return AgentConfigDocument(
            fileName = name,
            path = toAbsolutePath().toString(),
            content = content,
        )
    }
}

