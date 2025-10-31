package aifactory.ai

import aifactory.core.Result
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock

class AgentExecutor(
    private val executionDelayMs: Long = 150,
) {

    suspend fun execute(agent: AgentProfile, task: AgentTask): Result<AgentExecution> {
        if (executionDelayMs > 0) {
            delay(executionDelayMs)
        }
        val output =
            "Simulación completada por ${agent.name}: ${task.name} → ${task.description.take(120)}"
        val execution = AgentExecution(
            agent = agent,
            task = task,
            output = output,
            timestamp = Clock.System.now(),
            success = true,
        )
        return Result.Success(execution)
    }
}

