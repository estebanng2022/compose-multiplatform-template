package aifactory.data.repositories

import aifactory.core.Result
import aifactory.data.datasources.LocalFileDataSource
import aifactory.domain.models.RuleSet
import kotlin.math.abs

class RuleSetRepositoryImpl(
    private val fileDataSource: LocalFileDataSource
) : RuleSetRepository {

    override suspend fun getRuleSet(path: String): Result<RuleSet> {
        val contentResult = fileDataSource.readFile(path)
        return when (contentResult) {
            is Result.Failure -> contentResult
            is Result.Success -> {
                val checksum = simpleChecksum(contentResult.value)
                Result.Success(
                    RuleSet(
                        filePath = path,
                        checksum = checksum
                    )
                )
            }
        }
    }

    override suspend fun saveRuleSet(ruleSet: RuleSet): Result<Unit> {
        // Saving a ruleset is equivalent to ensuring the rules file exists; here we no-op
        // because callers should write the file content via FileIO. We return success.
        return Result.Success(Unit)
    }

    override suspend fun validateRuleSet(ruleSet: RuleSet): Result<Boolean> {
        val exists = fileDataSource.exists(ruleSet.filePath)
        return Result.Success(exists)
    }

    private fun simpleChecksum(content: String): String {
        // Very simple checksum to avoid adding crypto deps
        val hash = content.fold(0) { acc, c -> acc * 31 + c.code }
        return abs(hash).toString(16)
    }
}
