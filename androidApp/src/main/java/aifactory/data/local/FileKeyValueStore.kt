package aifactory.data.local

import aifactory.core.Logx
import aifactory.core.Paths
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * A file-based implementation of the KeyValueStore.
 * This implementation is a stub and only handles the settings file.
 */
class FileKeyValueStore(private val context: Context) : KeyValueStore {

    override suspend fun read(key: String): String? = withContext(Dispatchers.IO) {
        val file = getFileForKey(key) ?: return@withContext null
        if (!file.exists()) {
            return@withContext null
        }
        try {
            file.readText()
        } catch (e: Exception) {
            Logx.e(msg = "Failed to read file for key: $key", t = e)
            null
        }
    }

    override suspend fun write(key: String, value: String) = withContext(Dispatchers.IO) {
        val file = getFileForKey(key) ?: return@withContext
        try {
            file.writeText(value)
        } catch (e: Exception) {
            Logx.e(msg = "Failed to write file for key: $key", t = e)
        }
    }

    private fun getFileForKey(key: String): File? {
        return when (key) {
            "settings" -> Paths.settingsFile(context)
            else -> {
                Logx.e(msg = "Unsupported key for FileKeyValueStore: $key")
                null
            }
        }
    }
}

