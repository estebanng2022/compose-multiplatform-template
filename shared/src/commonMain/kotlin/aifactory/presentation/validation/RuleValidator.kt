package aifactory.presentation.validation

import aifactory.core.Result
import aifactory.domain.models.RuleSet

class RuleValidator {
    fun validate(ruleSet: RuleSet): Result<Unit> {
        if (ruleSet.filePath.isBlank()) return Result.Failure(IllegalArgumentException("Rule filePath is blank"))
        if (ruleSet.checksum.isBlank()) return Result.Failure(IllegalArgumentException("Rule checksum is blank"))
        return Result.Success(Unit)
    }
}


