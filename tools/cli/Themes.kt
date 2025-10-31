package tools.cli

import aifactory.core.JvmFileIO
import aifactory.core.Result
import aifactory.presentation.validation.ThemeValidator
import tools.ArtifactPaths
import java.io.File

object ThemesCli {
    private val roots = listOf(
        // seeds in repo
        File("shared/src/commonMain/kotlin/aifactory/ui/themes/seeds"),
        // runtime themes under filesDir-equivalent when running CLI in repo (fallback to local dir)
        File("shared/ui/themes"),
    )

    fun list(): Result<Unit> {
        val themes = discoverThemes()
        val log = themes.joinToString(prefix = "[", postfix = "]") { (project, theme, path) ->
            """{"project":"$project","theme":"$theme","path":"${escape(path)}"}"""
        }
        ArtifactPaths.writeLog(ArtifactPaths.timestampedLogName("themes-list"), "{" + "\"status\":\"passed\",\"themes\":$log" + "}")
        themes.forEach { println("${it.first}/${it.second} -> ${it.third}") }
        return Result.Success(Unit)
    }

    fun validate(path: String): Result<Unit> {
        val io = JvmFileIO()
        val res = runBlockingRead(io, path)
        val payload = when (res) {
            is Result.Failure -> """{"status":"failed","error":"${escape(res.exception.message ?: "read error")}"}"""
            is Result.Success -> {
                val report = ThemeValidator.validateYaml(res.value)
                if (report.passed) {
                    """{"status":"passed","issues":[]}"""
                } else {
                    val issues = report.issues.joinToString(prefix = "[", postfix = "]") { "\"${escape(it)}\"" }
                    """{"status":"failed","issues":$issues}"""
                }
            }
        }
        ArtifactPaths.writeLog(ArtifactPaths.timestampedLogName("themes-validate"), payload)
        return if (payload.contains("\"failed\"") && !payload.contains("issues":[])) Result.Failure(Exception("theme validation failed")) else Result.Success(Unit)
    }

    fun apply(project: String, theme: String): Result<Unit> {
        val io = JvmFileIO()
        val path = "configs/projects/$project.yaml"
        val content = "theme: $project/$theme\n"
        val res = runBlockingWrite(io, path, content)
        val payload = when (res) {
            is Result.Success -> """{"status":"passed","project":"$project","theme":"$theme"}"""
            is Result.Failure -> """{"status":"failed","error":"${escape(res.exception.message ?: "write error")}"}"""
        }
        ArtifactPaths.writeLog(ArtifactPaths.timestampedLogName("themes-apply"), payload)
        return res
    }

    private fun discoverThemes(): List<Triple<String, String, String>> {
        val result = mutableListOf<Triple<String, String, String>>()
        roots.filter { it.exists() }.forEach { root ->
            root.listFiles { f -> f.isDirectory }?.forEach { projectDir ->
                projectDir.listFiles { f -> f.isDirectory }?.forEach { themeDir ->
                    val yaml = File(themeDir, "theme.yaml")
                    if (yaml.exists()) result += Triple(projectDir.name, themeDir.name, yaml.path.replace('\\', '/'))
                }
            }
        }
        return result
    }

    private fun escape(value: String): String = value.replace("\\", "\\\\").replace("\"", "\\\"")

    private fun runBlockingRead(io: JvmFileIO, path: String): Result<String> =
        kotlinx.coroutines.runBlocking { io.read(path) }

    private fun runBlockingWrite(io: JvmFileIO, path: String, content: String): Result<Unit> =
        kotlinx.coroutines.runBlocking { io.write(path, content) }
}

