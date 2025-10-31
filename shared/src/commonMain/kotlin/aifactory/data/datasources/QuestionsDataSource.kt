package aifactory.data.datasources

import aifactory.core.Result
import aifactory.domain.models.QuestionSet
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.serializer

class QuestionsDataSource(private val file: LocalFileDataSource) {
    suspend fun read(path: String): Result<QuestionSet> {
        val content = file.readFile(path)
        return when (content) {
            is Result.Failure -> content
            is Result.Success -> parse(content.value)
        }
    }

    private fun parse(text: String): Result<QuestionSet> = try {
        val qs: QuestionSet = Yaml.default.decodeFromString(serializer(), text)
        Result.Success(qs)
    } catch (e: Exception) {
        Result.Failure(e)
    }
}

