package aifactory.ai

import aifactory.core.Result
import java.io.File

actual fun defaultAgentConfigSource(): AgentConfigSource = AndroidAgentConfigSource()

class AndroidAgentConfigSource(
    private val baseDir: File = File("")
) : AgentConfigSource {
    override suspend fun listAgentConfigs(directory: String): Result<List<AgentConfigDocument>> {
        val dir = File(baseDir, directory)
        if (!dir.exists() || !dir.isDirectory) {
            return Result.Success(emptyList())
        }
        return try {
            val docs = dir.listFiles { f -> f.isFile && f.name.endsWith(".yaml", ignoreCase = true) }
                ?.sortedBy { it.name }
                ?.map { f ->
                    AgentConfigDocument(
                        fileName = f.name,
                        path = f.absolutePath,
                        content = f.readText()
                    )
                } ?: emptyList()
            Result.Success(docs)
        } catch (t: Throwable) {
            Result.Failure(t)
        }
    }
}
