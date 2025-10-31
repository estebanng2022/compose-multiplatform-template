package aifactory.data.repositories

import aifactory.core.Result
import aifactory.domain.models.Agent

interface AgentRepository {
    suspend fun getAgent(id: String): Result<Agent>
    suspend fun saveAgent(agent: Agent): Result<Unit>
    suspend fun getAgentsFromConfig(path: String): Result<List<Agent>>
}
