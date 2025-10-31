package aifactory.data.datasources

import aifactory.core.Result
import aifactory.domain.models.Theme
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.serializer

class ThemeDataSource(
    private val file: LocalFileDataSource,
) {
    suspend fun read(path: String): Result<Theme> {
        val content = file.readFile(path)
        return when (content) {
            is Result.Failure -> content
            is Result.Success -> parse(content.value)
        }
    }

    suspend fun write(path: String, theme: Theme): Result<Unit> {
        val serialized = try {
            val text = Yaml.default.encodeToString(serializer(), theme)
            Result.Success(text)
        } catch (e: Exception) {
            Result.Failure(e)
        }
        return when (serialized) {
            is Result.Failure -> serialized
            is Result.Success -> file.writeFile(path, serialized.value)
        }
    }

    private fun parse(text: String): Result<Theme> = try {
        val t: Theme = Yaml.default.decodeFromString(serializer(), text)
        Result.Success(t)
    } catch (e: Exception) {
        Result.Failure(e)
    }
}

