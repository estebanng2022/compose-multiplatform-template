package aifactory.data.repositories

import aifactory.core.Result
import aifactory.domain.models.QuestionSet

interface QuestionsRepository {
    suspend fun loadGlobal(): Result<QuestionSet>
    suspend fun loadForProject(projectId: String): Result<QuestionSet>
    suspend fun mergeOverrides(global: QuestionSet, project: QuestionSet): Result<QuestionSet>
}

