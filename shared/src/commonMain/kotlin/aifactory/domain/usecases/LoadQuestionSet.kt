package aifactory.domain.usecases

import aifactory.core.Result
import aifactory.data.repositories.QuestionsRepository
import aifactory.domain.models.Question
import aifactory.domain.models.QuestionSet

class LoadQuestionSet(private val repository: QuestionsRepository) {
    suspend operator fun invoke(projectId: String?): Result<QuestionSet> {
        val global = repository.loadGlobal()
        return when (global) {
            is Result.Failure -> global
            is Result.Success -> {
                if (projectId == null) return Result.Success(global.value)
                val project = repository.loadForProject(projectId)
                when (project) {
                    is Result.Failure -> Result.Success(global.value)
                    is Result.Success -> repository.mergeOverrides(global.value, project.value)
                }
            }
        }
    }
}

