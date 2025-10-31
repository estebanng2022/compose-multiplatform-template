package aifactory.domain.usecases

import aifactory.core.Result
import aifactory.data.repositories.ThemeRepository
import aifactory.domain.models.Theme

class CloneTheme(private val repository: ThemeRepository) {
    suspend operator fun invoke(
        sourceProject: String,
        sourceTheme: String,
        targetProject: String,
        targetTheme: String,
    ): Result<Theme> {
        val src = repository.get(sourceProject, sourceTheme)
        return when (src) {
            is Result.Failure -> src
            is Result.Success -> {
                val cloned = src.value.copy(name = targetTheme)
                when (val saved = repository.save(cloned, projectId = targetProject)) {
                    is Result.Failure -> saved
                    is Result.Success -> Result.Success(cloned)
                }
            }
        }
    }
}

