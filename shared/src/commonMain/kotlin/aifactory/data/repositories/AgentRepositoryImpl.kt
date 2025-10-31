package aifactory.data.repositories

import aifactory.ai.AgentConfigDocument
import aifactory.ai.defaultAgentConfigSource
import aifactory.core.Config
import aifactory.core.Result
import aifactory.data.datasources.LocalFileDataSource
import aifactory.data.datasources.YamlDataSource
import aifactory.domain.models.Agent
import aifactory.domain.models.RuleSet

class AgentRepositoryImpl(
    private val fileDataSource: LocalFileDataSource,
    private val yamlDataSource: YamlDataSource,
) : AgentRepository {

    override suspend fun getAgent(id: String): Result<Agent> {
        val dir = Config.Paths.AGENTS_DIR
        val docsRes = defaultAgentConfigSource().listAgentConfigs(dir)
        return when (docsRes) {
            is Result.Failure -> docsRes
            is Result.Success -> {
                val agent = docsRes.value.mapNotNull { parseAgent(it) }.firstOrNull { it.id == id }
                if (agent == null) Result.Failure(NoSuchElementException("Agent $id not found"))
                else Result.Success(agent)
            }
        }
    }

    override suspend fun saveAgent(agent: Agent): Result<Unit> {
        val yaml = buildString {
            appendLine("id: ${agent.id}")
            appendLine("name: ${agent.name}")
            appendLine("role: ${agent.role}")
            appendLine("rules: ${agent.rules.filePath}")
            appendLine("tools:")
            agent.tools.forEach { appendLine("  - $it") }
        }
        val path = "${Config.Paths.AGENTS_DIR}/${agent.id}.yaml"
        return fileDataSource.writeFile(path, yaml)
    }

    override suspend fun getAgentsFromConfig(path: String): Result<List<Agent>> {
        // If path is a directory, scan *.yaml files; if it is a file, try parsing it as a list of agents.
        val docsRes = defaultAgentConfigSource().listAgentConfigs(path)
        return when (docsRes) {
            is Result.Failure -> docsRes
            is Result.Success -> {
                val agents = docsRes.value.mapNotNull { parseAgent(it) }
                Result.Success(agents)
            }
        }
    }

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
