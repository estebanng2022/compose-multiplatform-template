package aifactory.desktop

import aifactory.platform.UpdateInfo
import aifactory.platform.UpdateService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URL

@Serializable
data class UpdateJson(val latest: String, val url: String, val notes: String? = null)

class SimpleUpdateService(private val updateJsonUrl: String, private val currentVersion: String) : UpdateService {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun check(): UpdateInfo? = withContext(Dispatchers.IO) {
        try {
            val content = URL(updateJsonUrl).readText()
            val update = json.decodeFromString<UpdateJson>(content)

            // Basic version comparison (e.g., "1.0.1" > "1.0.0")
            if (compareVersions(update.latest, currentVersion) > 0) {
                UpdateInfo(update.latest, update.url, update.notes)
            } else {
                null
            }
        } catch (e: Exception) {
            // Log error, but don't crash
            e.printStackTrace()
            null
        }
    }

    // Simplified version comparison. A robust implementation should handle more complex cases.
    private fun compareVersions(v1: String, v2: String): Int {
        val parts1 = v1.split(".").map { it.toIntOrNull() ?: 0 }
        val parts2 = v2.split(".").map { it.toIntOrNull() ?: 0 }
        val maxParts = maxOf(parts1.size, parts2.size)
        for (i in 0 until maxParts) {
            val part1 = parts1.getOrNull(i) ?: 0
            val part2 = parts2.getOrNull(i) ?: 0
            if (part1 > part2) return 1
            if (part1 < part2) return -1
        }
        return 0
    }
}
