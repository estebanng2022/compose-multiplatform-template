package aifactory.data.local

/**
 * A simple key-value store contract for reading and writing string-based data.
 */
interface KeyValueStore {
    suspend fun read(key: String): String?
    suspend fun write(key: String, value: String)
}
