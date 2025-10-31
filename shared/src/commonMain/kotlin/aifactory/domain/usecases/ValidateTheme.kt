package aifactory.domain.usecases

import aifactory.core.Result
import aifactory.data.repositories.ThemeRepository
import aifactory.domain.models.Theme

data class ThemeValidation(val passed: Boolean, val issues: List<String>)

class ValidateTheme(private val repository: ThemeRepository) {
    suspend operator fun invoke(theme: Theme): Result<ThemeValidation> = repository.validate(theme)
}

