package aifactory.core

interface FileIO {
    suspend fun read(path: String): Result<String>
    suspend fun write(path: String, content: String): Result<Unit>
    suspend fun delete(path: String): Result<Unit>
    suspend fun exists(path: String): Boolean
}
