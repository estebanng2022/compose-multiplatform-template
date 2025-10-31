package aifactory.core

class Config {
    // Default paths for data layer
    object Paths {
        const val AGENTS_DIR: String = "configs/agents"
        const val PIPELINES_FILE: String = "configs/pipelines.yaml"
        const val RULESETS_DIR: String = "configs/rules"
        const val RUNS_DIR: String = "artifacts/runs"
        const val ARTIFACTS_DIR: String = "artifacts/files"
        const val LOGS_DIR: String = "artifacts/logs"
    }
}
