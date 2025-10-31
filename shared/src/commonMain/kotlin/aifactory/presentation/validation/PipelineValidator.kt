package aifactory.presentation.validation

import aifactory.core.Result
import aifactory.domain.models.Pipeline

class PipelineValidator {
    fun validate(pipeline: Pipeline): Result<Unit> {
        if (pipeline.name.isBlank()) return Result.Failure(IllegalArgumentException("Pipeline name is blank"))
        if (pipeline.tasks.isEmpty()) return Result.Failure(IllegalArgumentException("Pipeline has no tasks"))
        return Result.Success(Unit)
    }
}


