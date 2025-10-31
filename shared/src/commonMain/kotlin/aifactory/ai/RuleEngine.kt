package aifactory.ai

import aifactory.core.Result
import aifactory.domain.models.Pipeline
import aifactory.domain.models.Task
import kotlinx.datetime.Clock

class RuleEngine(
    private val localAiManager: LocalAIManager,
    private val agentExecutor: AgentExecutor,
) {

    suspend fun simulatePipeline(pipeline: Pipeline): Result<PipelineSimulation> =
        runPipeline(pipeline.name, pipeline.tasks)

    suspend fun simulateTasks(name: String, tasks: List<Task>): Result<PipelineSimulation> =
        runPipeline(name, tasks)

    private suspend fun runPipeline(name: String, tasks: List<Task>): Result<PipelineSimulation> {
        val agentsResult = localAiManager.loadAgents()
        if (agentsResult is Result.Failure) {
            return agentsResult
        }
        val agents = (agentsResult as Result.Success).value.map { it.profile }
        if (agents.isEmpty()) {
            return Result.Failure(IllegalStateException("No se encontraron agentes locales en configs/agents*.yaml"))
        }

        val startedAt = Clock.System.now()
        val steps = mutableListOf<RuleStepResult>()

        tasks.forEachIndexed { index, task ->
            val assignedAgent = agents[index % agents.size]
            val agentTask = AgentTask(
                id = task.id,
                name = task.name,
                description = task.description,
            )
            val executionResult = agentExecutor.execute(assignedAgent, agentTask)
            when (executionResult) {
                is Result.Success -> {
                    steps += RuleStepResult(
                        stepIndex = index,
                        task = agentTask,
                        agent = assignedAgent,
                        status = StepStatus.Success,
                        output = executionResult.value.output,
                    )
                }

                is Result.Failure -> {
                    steps += RuleStepResult(
                        stepIndex = index,
                        task = agentTask,
                        agent = assignedAgent,
                        status = StepStatus.Failure,
                        output = executionResult.exception.message ?: "Fallo en la ejecución del agente.",
                    )
                }
            }
        }

        val finishedAt = Clock.System.now()
        return Result.Success(
            PipelineSimulation(
                pipelineName = name,
                steps = steps,
                startedAt = startedAt,
                finishedAt = finishedAt,
            ),
        )
    }
}

