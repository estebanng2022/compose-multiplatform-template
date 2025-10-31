package aifactory.domain.usecases

import aifactory.core.Result
import aifactory.data.repositories.ThemeRepository

class ApplyThemeToProject(private val repository: ThemeRepository) {
    suspend operator fun invoke(projectId: String, themeName: String): Result<Unit> =
        repository.apply(projectId, themeName)
}

