package aifactory.ai

import aifactory.core.Result

/**
 * Abstrae la manera de obtener configuraciones de agentes desde el sistema de archivos.
 */
interface AgentConfigSource {
    suspend fun listAgentConfigs(directory: String): Result<List<AgentConfigDocument>>
}

data class AgentConfigDocument(
    val fileName: String,
    val path: String,
    val content: String,
)

expect fun defaultAgentConfigSource(): AgentConfigSource

