package aifactory.domain.usecases

import aifactory.ai.AgentConfigDocument
import aifactory.ai.defaultAgentConfigSource
import aifactory.core.Result
import aifactory.data.repositories.AgentRepository
import aifactory.domain.models.Agent
import aifactory.domain.models.RuleSet

/**
 * Use case to register a new AI agent from a YAML config file.
 */
class RegisterAgent(private val repository: AgentRepository) {

    suspend operator fun invoke(configPath: String): Result<Agent> {
        val src = defaultAgentConfigSource()
        val listRes = src.listAgentConfigs(parentDirOf(configPath))
        val doc = when (listRes) {
            is Result.Failure -> return listRes
            is Result.Success -> listRes.value.firstOrNull { it.path.endsWith(normalize(configPath)) }
        } ?: AgentConfigDocument(fileName = configPath.substringAfterLast('/'), path = configPath, content = return Result.Failure(IllegalArgumentException("Agent config not found: $configPath")) as Nothing)

        val agent = parseAgent(doc) ?: return Result.Failure(IllegalArgumentException("Invalid agent yaml: $configPath"))
        return when (val save = repository.saveAgent(agent)) {
            is Result.Failure -> save
            is Result.Success -> Result.Success(agent)
        }
    }

    private fun parentDirOf(path: String): String =
        path.substringBeforeLast('/', missingDelimiterValue = ".")

    private fun normalize(path: String): String = path.replace('\\', '/')

    private fun parseAgent(doc: AgentConfigDocument): Agent? {
        val map = mutableMapOf<String, Any>()
        val tools = mutableListOf<String>()
        var current: String? = null
        doc.content.lineSequence().forEach { raw ->
            val line = raw.trim()
            if (line.isEmpty() || line.startsWith("#")) return@forEach
            when {
                line.endsWith(":") -> current = line.removeSuffix(":").trim()
                line.startsWith("-") -> if (current == "tools") tools += line.removePrefix("-").trim().trim('"')
                ":" in line -> {
                    val (k, v) = line.split(":", limit = 2)
                    map[k.trim()] = v.trim().trim('"')
                    current = k.trim()
                }
            }
        }
        val id = (map["id"] as? String)?.ifBlank { null } ?: doc.fileName.substringBefore('.')
        val name = (map["name"] as? String)?.ifBlank { null } ?: id
        val role = (map["role"] as? String)?.ifBlank { null } ?: "assistant"
        val rulesPath = (map["rules"] as? String) ?: (map["rulesPath"] as? String) ?: "configs/rules/$id.yaml"
        val checksum = rulesPath.hashCode().toString()
        return Agent(
            id = id,
            name = name,
            role = role,
            rules = RuleSet(filePath = rulesPath, checksum = checksum),
            tools = tools.toList(),
        )
    }
}
