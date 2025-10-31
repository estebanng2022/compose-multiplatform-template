package aifactory.data.datasources

import aifactory.core.FileIO
import aifactory.core.Result

/**
 * A concrete data source for reading and writing local files.
 * It uses the FileIO interface from the Core layer, which will have a platform-specific implementation.
 */
class LocalFileDataSource(private val fileIO: FileIO) {

    suspend fun readFile(path: String): Result<String> {
        return fileIO.read(path)
    }

    suspend fun writeFile(path: String, content: String): Result<Unit> {
        return fileIO.write(path, content)
    }

    suspend fun exists(path: String): Boolean {
        return try { fileIO.exists(path) } catch (_: Exception) { false }
    }
}
