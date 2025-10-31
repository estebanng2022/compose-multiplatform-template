package tools.cli

import aifactory.core.Result
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printUsage()
        exitProcess(1)
    }

    val outcome = when (args[0]) {
        "validate" -> ValidateCli.run()
        "snapshots" -> handleSnapshots(args.drop(1))
        "a11y" -> handleA11y(args.drop(1))
        "api-surface" -> handleApiSurface(args.drop(1))
        "themes" -> handleThemes(args.drop(1))
        "agents" -> handleAgents(args.drop(1))
        "pipelines" -> handlePipelines(args.drop(1))
        "run" -> handleRun(args.drop(1))
        else -> {
            println("Unknown command: ${args[0]}")
            printUsage()
            Result.Failure(IllegalArgumentException("unknown command"))
        }
    }

    when (outcome) {
        is Result.Success -> exitProcess(0)
        is Result.Failure -> {
            System.err.println(outcome.exception.message ?: "Command failed")
            exitProcess(1)
        }
    }
}

private fun handleSnapshots(args: List<String>): Result<Unit> {
    if (args.isEmpty()) {
        println("Missing snapshots subcommand (record|verify)")
        return Result.Failure(IllegalArgumentException("missing snapshots subcommand"))
    }
    return when (args[0]) {
        "record" -> SnapshotsCli.record()
        "verify" -> SnapshotsCli.verify()
        else -> {
            println("Unknown snapshots subcommand: ${args[0]}")
            Result.Failure(IllegalArgumentException("unknown snapshots subcommand"))
        }
    }
}

private fun handleA11y(args: List<String>): Result<Unit> =
    if (args.firstOrNull() == "check") {
        A11yCli.check()
    } else {
        println("Usage: a11y check")
        Result.Failure(IllegalArgumentException("unknown a11y subcommand"))
    }

private fun handleApiSurface(args: List<String>): Result<Unit> =
    if (args.firstOrNull() == "check") {
        ApiSurfaceCli.check()
    } else {
        println("Usage: api-surface check")
        Result.Failure(IllegalArgumentException("unknown api-surface subcommand"))
    }

private fun printUsage() {
    println(
        """
        Available commands:
          validate
          snapshots record|verify
          a11y check
          api-surface check
          themes list
          themes validate --path <yaml>
          themes apply --project <id> --theme <name>
          agents scan [--dir <path>]
          pipelines list
          run start --pipeline <id>
          run logs --id <runId>
        """.trimIndent(),
    )
}

private fun handleAgents(args: List<String>): Result<Unit> = runBlocking {
    if (args.firstOrNull() != "scan") {
        println("Usage: agents scan [--dir <path>]")
        return@runBlocking Result.Failure(IllegalArgumentException("unknown agents subcommand"))
    }
    val dir = args.drop(1).let { rest ->
        val idx = rest.indexOf("--dir")
        if (idx >= 0 && idx + 1 < rest.size) rest[idx + 1] else null
    }
    AgentsCli.scan(dir)
}

private fun handlePipelines(args: List<String>): Result<Unit> = runBlocking {
    if (args.firstOrNull() != "list") {
        println("Usage: pipelines list")
        return@runBlocking Result.Failure(IllegalArgumentException("unknown pipelines subcommand"))
    }
    PipelinesCli.list()
}

private fun handleThemes(args: List<String>): Result<Unit> = runBlocking {
    when (args.firstOrNull()) {
        "list" -> ThemesCli.list()
        "validate" -> {
            val path = args.drop(1).let { r -> val i = r.indexOf("--path"); if (i>=0 && i+1<r.size) r[i+1] else null }
            if (path == null) { println("Usage: themes validate --path <yaml>"); Result.Failure(IllegalArgumentException("missing path")) }
            else ThemesCli.validate(path)
        }
        "apply" -> {
            val rest = args.drop(1)
            val pj = rest.let { r -> val i = r.indexOf("--project"); if (i>=0 && i+1<r.size) r[i+1] else null }
            val th = rest.let { r -> val i = r.indexOf("--theme"); if (i>=0 && i+1<r.size) r[i+1] else null }
            if (pj==null || th==null) { println("Usage: themes apply --project <id> --theme <name>"); Result.Failure(IllegalArgumentException("missing args")) }
            else ThemesCli.apply(pj, th)
        }
        else -> { println("Usage: themes list|validate|apply"); Result.Failure(IllegalArgumentException("unknown themes subcommand")) }
    }
}

private fun handleRun(args: List<String>): Result<Unit> = runBlocking {
    when (args.firstOrNull()) {
        "start" -> {
            val pipelineId = args.drop(1).let { rest ->
                val idx = rest.indexOf("--pipeline")
                if (idx >= 0 && idx + 1 < rest.size) rest[idx + 1] else null
            }
            if (pipelineId == null) {
                println("Usage: run start --pipeline <id>")
                Result.Failure(IllegalArgumentException("missing pipeline id"))
            } else {
                when (val res = RunsCli.start(pipelineId)) {
                    is Result.Success -> {
                        println("runId: ${res.value}")
                        Result.Success(Unit)
                    }
                    is Result.Failure -> Result.Failure(res.exception)
                }
            }
        }
        "logs" -> {
            val runId = args.drop(1).let { rest ->
                val idx = rest.indexOf("--id")
                if (idx >= 0 && idx + 1 < rest.size) rest[idx + 1] else null
            }
            if (runId == null) {
                println("Usage: run logs --id <runId>")
                Result.Failure(IllegalArgumentException("missing run id"))
            } else {
                RunsCli.logs(runId)
            }
        }
        else -> {
            println("Usage: run start --pipeline <id> | run logs --id <runId>")
            Result.Failure(IllegalArgumentException("unknown run subcommand"))
        }
    }
}
