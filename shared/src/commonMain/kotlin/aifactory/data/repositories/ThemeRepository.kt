package aifactory.data.repositories

import aifactory.core.Result
import aifactory.domain.models.Theme
import aifactory.domain.usecases.ThemeValidation

interface ThemeRepository {
    suspend fun list(projectId: String? = null): Result<List<Pair<String, Theme>>>
    suspend fun get(projectId: String, themeName: String): Result<Theme>
    suspend fun save(theme: Theme, projectId: String? = null): Result<Unit>
    suspend fun validate(theme: Theme): Result<ThemeValidation>
    suspend fun apply(projectId: String, themeName: String): Result<Unit>
}

