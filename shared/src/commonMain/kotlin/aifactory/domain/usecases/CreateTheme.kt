package aifactory.domain.usecases

import aifactory.core.Result
import aifactory.data.repositories.ThemeRepository
import aifactory.domain.models.Theme

class CreateTheme(private val repository: ThemeRepository) {
    suspend operator fun invoke(theme: Theme): Result<Theme> {
        val saved = repository.save(theme)
        return when (saved) {
            is Result.Failure -> saved
            is Result.Success -> Result.Success(theme)
        }
    }
}

