package aifactory.telemetry

expect fun createCrashReporter(): CrashReporter

object Crash {
    val reporter: CrashReporter by lazy {
        createCrashReporter()
    }
}
