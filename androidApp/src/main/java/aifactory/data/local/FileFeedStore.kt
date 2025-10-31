package aifactory.data.local

import aifactory.core.Logx
import aifactory.core.Paths
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File

class FileFeedStore(private val context: Context) : FeedStore {
    private val feedFile: File by lazy { Paths.feedFile(context) }

    override suspend fun read(): FeedSnapshotV1? = withContext(Dispatchers.IO) {
        if (!feedFile.exists()) {
            return@withContext null
        }
        try {
            val jsonString = feedFile.readText()
            Json.decodeFromString<FeedSnapshotV1>(jsonString)
        } catch (e: Exception) {
            Logx.e(msg = "Failed to read or parse feed file", t = e)
            clear()
            null
        }
    }

    override suspend fun write(snapshot: FeedSnapshotV1): Boolean = withContext(Dispatchers.IO) {
        try {
            val jsonString = Json.encodeToString(FeedSnapshotV1.serializer(), snapshot)
            feedFile.writeText(jsonString)
            true
        } catch (e: Exception) {
            Logx.e(msg = "Failed to write feed file", t = e)
            false
        }
    }

    override suspend fun clear(): Boolean = withContext(Dispatchers.IO) {
        try {
            if (feedFile.exists()) {
                feedFile.delete()
            } else {
                true
            }
        } catch (e: Exception) {
            Logx.e(msg = "Failed to clear feed file", t = e)
            false
        }
    }
}

