package aifactory.data

import aifactory.core.Result
import aifactory.presentation.state.ThemeEntry
import aifactory.presentation.viewmodels.ThemeLister
import java.io.File

class AndroidThemeLister(private val baseDir: File) : ThemeLister {
    override suspend fun list(projectId: String?): Result<List<ThemeEntry>> {
        val result = mutableListOf<ThemeEntry>()
        // Project themes under filesDir/shared/ui/themes/<project>/<theme>/theme.yaml
        val projectRoot = File(baseDir, "shared/ui/themes")
        if (projectRoot.exists()) {
            projectRoot.listFiles { f -> f.isDirectory }?.forEach { project ->
                if (projectId == null || project.name == projectId) {
                    project.listFiles { tf -> tf.isDirectory }?.forEach { theme ->
                        val yaml = File(theme, "theme.yaml")
                        if (yaml.exists()) result += ThemeEntry(project.name, theme.name, yaml.relativeTo(baseDir).path.replace('\\', '/'), scope = "project")
                    }
                }
            }
        }
        // Seeds are optional; only if copied under filesDir
        val seedsRoot = File(baseDir, "shared/src/commonMain/kotlin/aifactory/ui/themes/seeds")
        if (seedsRoot.exists()) {
            seedsRoot.listFiles { f -> f.isDirectory }?.forEach { seed ->
                val yaml = File(seed, "theme.yaml")
                if (yaml.exists()) result += ThemeEntry("seeds", seed.name, yaml.relativeTo(baseDir).path.replace('\\', '/'), scope = "global")
            }
        }
        return Result.Success(result)
    }
}

