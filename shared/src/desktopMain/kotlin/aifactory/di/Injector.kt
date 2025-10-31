package aifactory.di

import aifactory.core.FileIO
import aifactory.core.Result
import aifactory.data.AiRepository
import aifactory.data.InMemoryAiRepository
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

private class DesktopFileIO(
    private val baseDir: Path = Paths.get("").toAbsolutePath(),
    private val charset: Charset = Charsets.UTF_8,
) : FileIO {
    private fun resolve(path: String): Path = baseDir.resolve(path).normalize()
    override suspend fun read(path: String): Result<String> = try {
        val p = resolve(path)
        Result.Success(Files.readString(p, charset))
    } catch (t: Throwable) { Result.Failure(t) }
    override suspend fun write(path: String, content: String): Result<Unit> = try {
        val p = resolve(path)
        Files.createDirectories(p.parent)
        Files.writeString(p, content, charset)
        Result.Success(Unit)
    } catch (t: Throwable) { Result.Failure(t) }
    override suspend fun delete(path: String): Result<Unit> = try {
        val p = resolve(path); Files.deleteIfExists(p); Result.Success(Unit)
    } catch (t: Throwable) { Result.Failure(t) }
    override suspend fun exists(path: String): Boolean = try { Files.exists(resolve(path)) } catch (_: Throwable) { false }
}

actual object Injector {
    actual fun provideRepository(): AiRepository = InMemoryAiRepository(DesktopFileIO())
}
