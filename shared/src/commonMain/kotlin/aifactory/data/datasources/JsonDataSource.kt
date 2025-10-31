package aifactory.data.datasources

import aifactory.core.Result
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * A data source for parsing JSON files using kotlinx.serialization.
 */
class JsonDataSource(@PublishedApi internal val fileDataSource: LocalFileDataSource) {

    @PublishedApi
    internal val json = Json { isLenient = true; ignoreUnknownKeys = true }

    suspend inline fun <reified T> parseFile(path: String): Result<T> {
        val fileContentResult = fileDataSource.readFile(path)
        if (fileContentResult is Result.Failure) {
            return fileContentResult
        }
        
        return try {
            val content = (fileContentResult as Result.Success).value
            val data = json.decodeFromString<T>(content)
            Result.Success(data)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    inline fun <reified T> serialize(data: T): Result<String> {
        return try {
            val content = json.encodeToString(serializer(), data)
            Result.Success(content)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
