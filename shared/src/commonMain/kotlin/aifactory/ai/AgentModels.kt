package aifactory.ai

import kotlinx.datetime.Instant

data class AgentProfile(
    val id: String,
    val name: String,
    val role: String,
    val rulesPath: String,
    val tools: List<String>,
    val sourcePath: String,
)

data class AgentStatus(
    val profile: AgentProfile,
    val isOnline: Boolean,
    val activeRules: List<String>,
    val lastHeartbeat: Instant,
    val logs: List<String>,
)

data class AgentTask(
    val id: String,
    val name: String,
    val description: String,
)

data class AgentExecution(
    val agent: AgentProfile,
    val task: AgentTask,
    val output: String,
    val timestamp: Instant,
    val success: Boolean,
)

data class PipelineSimulation(
    val pipelineName: String,
    val steps: List<RuleStepResult>,
    val startedAt: Instant,
    val finishedAt: Instant,
)

data class RuleStepResult(
    val stepIndex: Int,
    val task: AgentTask,
    val agent: AgentProfile?,
    val status: StepStatus,
    val output: String,
)

enum class StepStatus { Pending, Running, Success, Failure }

