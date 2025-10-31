package aifactory.data.repositories

import aifactory.core.Result
import aifactory.domain.models.RuleSet

interface RuleSetRepository {
    suspend fun getRuleSet(path: String): Result<RuleSet>
    suspend fun saveRuleSet(ruleSet: RuleSet): Result<Unit>
    suspend fun validateRuleSet(ruleSet: RuleSet): Result<Boolean>
}
