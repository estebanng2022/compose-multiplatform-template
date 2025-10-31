package aifactory.platform

import java.nio.file.Path
import java.nio.file.Paths
import java.io.File

object AppPaths {
    private val os = System.getProperty("os.name").lowercase()
    private val home = System.getProperty("user.home")

    private fun baseDir(): Path {
        // Portable mode: if there is a "portable.flag" next to the executable
        val cwd = Paths.get("").toAbsolutePath()
        if (File(cwd.resolve("portable.flag").toString()).exists()) return cwd.resolve("AiFactoryData")

        return when {
            os.contains("mac") -> Paths.get(home, "Library", "Application Support", "AiFactory")
            os.contains("win") -> Paths.get(System.getenv("APPDATA") ?: "$home\\AppData\\Roaming", "AiFactory")
            else -> Paths.get(System.getenv("XDG_DATA_HOME") ?: "$home/.local/share", "AiFactory")
        }
    }

    val dataDir: Path by lazy { baseDir().also { it.toFile().mkdirs() } }
    val cacheDir: Path by lazy {
        val p = when {
            os.contains("mac") -> Paths.get(home, "Library", "Caches", "AiFactory")
            os.contains("win") -> Paths.get(System.getenv("LOCALAPPDATA") ?: "$home\\AppData\\Local", "AiFactory", "Cache")
            else -> Paths.get(System.getenv("XDG_CACHE_HOME") ?: "$home/.cache", "AiFactory")
        }
        p.toFile().mkdirs(); p
    }
    val logsDir: Path by lazy { dataDir.resolve("logs").also { it.toFile().mkdirs() } }
    val configFile: Path by lazy { dataDir.resolve("config.json") }
}
