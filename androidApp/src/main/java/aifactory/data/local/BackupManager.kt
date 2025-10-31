package aifactory.data.local

import aifactory.core.Logx
import aifactory.core.Paths
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object BackupManager {

    data class Result(val success: Boolean, val message: String? = null)

    @Serializable
    private data class Meta(val app: String, val version: Int, val exportedAt: Long)

    private const val META_FILE_NAME = "meta.json"
    private const val SETTINGS_FILE_NAME = "settings.json"
    private const val FEED_FILE_NAME = "dashboard_feed.json"

    suspend fun exportAllToZip(context: Context, dest: Uri): Result = withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openOutputStream(dest)?.use { fileOutputStream ->
                ZipOutputStream(fileOutputStream.buffered()).use { zipOut ->
                    // 1. Add meta.json
                    val meta = Meta("AiFactory", 1, System.currentTimeMillis())
                    zipOut.putNextEntry(ZipEntry(META_FILE_NAME))
                    zipOut.write(Json.encodeToString(Meta.serializer(), meta).toByteArray())
                    zipOut.closeEntry()

                    // 2. Add settings.json
                    val settingsFile = Paths.settingsFile(context)
                    if (settingsFile.exists()) {
                        zipOut.putNextEntry(ZipEntry(SETTINGS_FILE_NAME))
                        settingsFile.inputStream().use { it.copyTo(zipOut) }
                        zipOut.closeEntry()
                    }

                    // 3. Add dashboard_feed.json
                    val feedFile = Paths.feedFile(context)
                    if (feedFile.exists()) {
                        zipOut.putNextEntry(ZipEntry(FEED_FILE_NAME))
                        feedFile.inputStream().use { it.copyTo(zipOut) }
                        zipOut.closeEntry()
                    }
                }
            }
            Result(true, "Export successful")
        } catch (e: Exception) {
            Logx.e(msg = "Export failed", t = e)
            Result(false, "Export failed: ${e.message}")
        }
    }

    suspend fun importAllFromZip(context: Context, src: Uri, overwrite: Boolean = true): Result = withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openInputStream(src)?.use { fileInputStream ->
                ZipInputStream(fileInputStream.buffered()).use { zipIn ->
                    var entry: ZipEntry?
                    var metaValidated = false
                    val foundFiles = mutableSetOf<String>()

                    while (zipIn.nextEntry.also { entry = it } != null) {
                        when (entry?.name) {
                            META_FILE_NAME -> {
                                val metaJson = zipIn.readBytes().toString(Charsets.UTF_8)
                                val meta = Json.decodeFromString(Meta.serializer(), metaJson)
                                if (meta.app != "AiFactory") {
                                    return@withContext Result(false, "Invalid backup file format.")
                                }
                                metaValidated = true
                            }
                            SETTINGS_FILE_NAME -> {
                                Paths.settingsFile(context).outputStream().use { zipIn.copyTo(it) }
                                foundFiles.add(SETTINGS_FILE_NAME)
                            }
                            FEED_FILE_NAME -> {
                                Paths.feedFile(context).outputStream().use { zipIn.copyTo(it) }
                                foundFiles.add(FEED_FILE_NAME)
                            }
                        }
                        zipIn.closeEntry()
                    }
                    
                    if (!metaValidated) return@withContext Result(false, "Backup metadata is missing.")
                    if (foundFiles.size < 2) return@withContext Result(false, "Backup is missing required files.")

                }
            }
            Result(true, "Import successful. Please restart the app.")
        } catch (e: Exception) {
            Logx.e(msg = "Import failed", t = e)
            Result(false, "Import failed: ${e.message}")
        }
    }
}

