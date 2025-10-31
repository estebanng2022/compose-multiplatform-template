package aifactory.domain.usecases

import aifactory.core.Logger
import aifactory.core.i
import aifactory.domain.models.Run
import kotlinx.coroutines.flow.Flow

/**
 * Use case to emit logs and status in real-time during an execution.
 */
class MonitorRun(private val logger: Logger) {
    // This might use a repository to get real-time updates from a data source.
    fun observe(runId: String): Flow<Run> {
        // TODO:
        // 1. Get the Run from a repository.
        // 2. Return a Flow that emits updates to the Run object.
        // 3. Log important status changes.
        logger.i("Starting to monitor run: $runId")
        return kotlinx.coroutines.flow.emptyFlow() // Placeholder
    }
}
