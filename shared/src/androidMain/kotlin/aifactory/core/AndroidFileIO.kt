package aifactory.core

import android.content.Context
import java.io.File

class AndroidFileIO(private val context: Context) : FileIO {
    private fun resolve(path: String): File {
        val base = context.filesDir
        return File(base, path)
    }

    override suspend fun read(path: String): Result<String> {
        return try {
            val file = resolve(path)
            val content = file.readText()
            Result.Success(content)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun write(path: String, content: String): Result<Unit> {
        return try {
            val file = resolve(path)
            file.parentFile?.mkdirs()
            file.writeText(content)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun delete(path: String): Result<Unit> {
        return try {
            val file = resolve(path)
            file.delete()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun exists(path: String): Boolean {
        return resolve(path).exists()
    }
}


