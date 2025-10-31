package aifactory.ai

import aifactory.core.Config
import aifactory.core.Result
import kotlinx.datetime.Clock

class LocalAIManager(
    private val configSource: AgentConfigSource,
    private val agentsDirectory: String = Config.Paths.AGENTS_DIR,
) {

    suspend fun loadAgents(): Result<List<AgentStatus>> {
        val documentsResult = configSource.listAgentConfigs(agentsDirectory)
        if (documentsResult is Result.Failure) {
            return documentsResult
        }
        val documents = (documentsResult as Result.Success).value
        if (documents.isEmpty()) {
            return Result.Success(emptyList())
        }

        val now = Clock.System.now()
        val statuses = documents.mapNotNull { document ->
            parseDocument(document)?.let { profile ->
                AgentStatus(
                    profile = profile,
                    isOnline = true,
                    activeRules = listOf(profile.rulesPath),
                    lastHeartbeat = now,
                    logs = listOf("Config loaded from ${profile.sourcePath}"),
                )
            }
        }

        return Result.Success(statuses)
    }

    private fun parseDocument(document: AgentConfigDocument): AgentProfile? {
        val lines = document.content.lines()
        if (lines.isEmpty()) return null

        val props = mutableMapOf<String, String>()
        val tools = mutableListOf<String>()
        var currentListKey: String? = null

        lines.forEach { line ->
            val trimmed = line.trim()
            if (trimmed.isEmpty() || trimmed.startsWith("#")) return@forEach

            when {
                trimmed.endsWith(":") -> {
                    currentListKey = trimmed.removeSuffix(":").trim()
                }
                trimmed.startsWith("-") -> {
                    val value = trimmed.removePrefix("-").trim().trim('"')
                    if (currentListKey == "tools") {
                        tools += value
                    }
                }
                ":" in trimmed -> {
                    val (key, raw) = trimmed.split(":", limit = 2)
                    val value = raw.trim().trim('"')
                    props[key.trim()] = value
                    currentListKey = key.trim()
                }
            }
        }

        val id = props["id"] ?: document.fileName.substringBefore('.')
        val name = props["name"] ?: id.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        val role = props["role"] ?: "assistant"
        val rulesPath = props["rules"] ?: props["rulesPath"] ?: "configs/rules/$id.yaml"
        val toolsList = tools.ifEmpty {
            props["tools"]
                ?.split(',')
                ?.map { it.trim() }
                ?.filter { it.isNotEmpty() }
                ?: emptyList()
        }

        return AgentProfile(
            id = id,
            name = name,
            role = role,
            rulesPath = rulesPath,
            tools = toolsList,
            sourcePath = document.path,
        )
    }

    companion object {
        fun default(): LocalAIManager = LocalAIManager(defaultAgentConfigSource())
    }
}

