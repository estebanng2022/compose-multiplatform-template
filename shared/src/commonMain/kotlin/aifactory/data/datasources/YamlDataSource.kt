package aifactory.data.datasources

import aifactory.core.Result
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.serializer

/**
 * A data source for parsing YAML files.
 * This will require a YAML parsing library for Kotlin Multiplatform.
 */
class YamlDataSource(@PublishedApi internal val fileDataSource: LocalFileDataSource) {

    // Parse a YAML file at [path] into type T
    suspend inline fun <reified T> parseFile(path: String): Result<T> {
        val fileContentResult = fileDataSource.readFile(path)
        if (fileContentResult is Result.Failure) {
            return fileContentResult
        }

        val content = (fileContentResult as Result.Success).value

        return try {
            val data = Yaml.default.decodeFromString(kotlinx.serialization.serializer<T>(), content)
            Result.Success(data)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    // Alias kept for compatibility with existing repository code
    suspend inline fun <reified T> loadFrom(path: String): Result<T> = parseFile(path)
}
