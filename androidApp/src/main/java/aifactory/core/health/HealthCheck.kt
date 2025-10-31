package aifactory.core.health

import aifactory.core.Logx
import aifactory.core.Paths
import aifactory.core.ServiceLocator
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object HealthCheck {

    suspend fun run(context: Context, minFreeBytes: Long = 10L * 1024 * 1024): HealthReport {
        val items = mutableListOf<HealthItem>()
        
        items.add(checkAppDataDir(context))
        items.add(checkWriteTest(context))
        items.add(checkSettingsJson(context))
        items.add(checkFreeSpace(context, minFreeBytes))
        items.add(checkRepository(context))

        return HealthReport(System.currentTimeMillis(), items)
    }

    fun summarize(report: HealthReport): HealthStatus {
        return when {
            report.items.any { it.status == HealthStatus.FAIL } -> HealthStatus.FAIL
            report.items.any { it.status == HealthStatus.WARN } -> HealthStatus.WARN
            else -> HealthStatus.PASS
        }
    }

    private fun checkAppDataDir(context: Context): HealthItem {
        return try {
            val dir = Paths.appDataDir(context)
            if (dir.exists() || dir.mkdirs()) {
                HealthItem("app_data_dir", "App Data Directory", HealthStatus.PASS, "Path: ${dir.absolutePath}")
            } else {
                HealthItem("app_data_dir", "App Data Directory", HealthStatus.FAIL, "Could not create directory")
            }
        } catch (e: Exception) {
            HealthItem("app_data_dir", "App Data Directory", HealthStatus.FAIL, e.message)
        }
    }

    private suspend fun checkWriteTest(context: Context): HealthItem = withContext(Dispatchers.IO) {
        try {
            val testFile = File(Paths.appDataDir(context), "health_check.tmp")
            testFile.writeText("ok")
            val content = testFile.readText()
            testFile.delete()
            if (content == "ok") {
                HealthItem("write_test", "Storage Write Test", HealthStatus.PASS)
            } else {
                HealthItem("write_test", "Storage Write Test", HealthStatus.FAIL, "Content mismatch")
            }
        } catch (e: Exception) {
            HealthItem("write_test", "Storage Write Test", HealthStatus.FAIL, e.message)
        }
    }

    private suspend fun checkSettingsJson(context: Context): HealthItem = withContext(Dispatchers.IO) {
        val settingsFile = Paths.settingsFile(context)
        if (!settingsFile.exists()) {
            return@withContext HealthItem("settings_json", "Settings File", HealthStatus.WARN, "File not found, will use defaults")
        }
        try {
            val content = settingsFile.readText()
            // A simple check, not a full JSON parse
            if (content.startsWith("{") && content.endsWith("}")) {
                HealthItem("settings_json", "Settings File", HealthStatus.PASS)
            } else {
                HealthItem("settings_json", "Settings File", HealthStatus.FAIL, "File appears to be corrupted")
            }
        } catch (e: Exception) {
            HealthItem("settings_json", "Settings File", HealthStatus.FAIL, e.message)
        }
    }

    private fun checkFreeSpace(context: Context, minFreeBytes: Long): HealthItem {
        val freeSpace = Paths.appDataDir(context).freeSpace
        return if (freeSpace >= minFreeBytes) {
            HealthItem("free_space", "Free Storage", HealthStatus.PASS, "${freeSpace / 1024 / 1024} MB free")
        } else {
            HealthItem("free_space", "Free Storage", HealthStatus.WARN, "Low storage: ${freeSpace / 1024 / 1024} MB free")
        }
    }

    private suspend fun checkRepository(context: Context): HealthItem {
        return try {
            ServiceLocator.repository(context).fetchSettings()
            HealthItem("repository", "Repository", HealthStatus.PASS)
        } catch (e: Exception) {
            HealthItem("repository", "Repository", HealthStatus.FAIL, e.message)
        }
    }
}
