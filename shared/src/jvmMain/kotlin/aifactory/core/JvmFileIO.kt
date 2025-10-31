package aifactory.core

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class JvmFileIO(
    private val baseDir: Path = Paths.get("").toAbsolutePath(),
    private val charset: Charset = Charsets.UTF_8,
) : FileIO {
    private fun resolve(path: String): Path = baseDir.resolve(path).normalize()

    override suspend fun read(path: String): Result<String> = try {
        val p = resolve(path)
        val content = Files.readString(p, charset)
        Result.Success(content)
    } catch (e: Exception) {
        Result.Failure(e)
    }

    override suspend fun write(path: String, content: String): Result<Unit> = try {
        val p = resolve(path)
        Files.createDirectories(p.parent)
        Files.writeString(p, content, charset)
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Failure(e)
    }

    override suspend fun delete(path: String): Result<Unit> = try {
        val p = resolve(path)
        Files.deleteIfExists(p)
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Failure(e)
    }

    override suspend fun exists(path: String): Boolean = try {
        Files.exists(resolve(path))
    } catch (_: Exception) {
        false
    }
}

