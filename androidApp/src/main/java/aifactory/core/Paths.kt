package aifactory.core

import android.content.Context
import java.io.File

/**
 * A centralized object for managing file paths within the app's internal storage.
 */
object Paths {
    const val SETTINGS_FILE = "settings.json"
    const val FEED_FILE = "dashboard_feed.json"

    fun appDataDir(context: Context): File {
        return context.filesDir
    }

    fun settingsFile(context: Context): File {
        return File(appDataDir(context), SETTINGS_FILE)
    }

    fun feedFile(context: Context): File {
        return File(appDataDir(context), FEED_FILE)
    }
}
