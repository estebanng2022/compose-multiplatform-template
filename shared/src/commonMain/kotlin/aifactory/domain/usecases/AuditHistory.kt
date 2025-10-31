package aifactory.domain.usecases

import aifactory.core.Result
import aifactory.domain.models.Run

/**
 * Use case to save and compare previous results (diffs).
 */
class AuditHistory {
    // Depends on a repository from the Data layer.
    suspend fun getHistory(pipelineId: String): Result<List<Run>> {
        // TODO: Fetch all runs for a given pipeline from the repository.
        return Result.Failure(NotImplementedError("AuditHistory.getHistory not implemented yet."))
    }

    suspend fun compareRuns(runIdA: String, runIdB: String): Result<String> {
        // TODO: 
        // 1. Fetch both runs from the repository.
        // 2. Compare their resulting artifacts.
        // 3. Generate a diff report.
        return Result.Failure(NotImplementedError("AuditHistory.compareRuns not implemented yet."))
    }
}
