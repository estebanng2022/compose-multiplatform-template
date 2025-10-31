package aifactory.telemetry

actual fun createCrashReporter(): CrashReporter = object : CrashReporter {
    override fun init() {}
    override fun capture(e: Throwable, context: Map<String, String>) { println("[Crash] ${'$'}{e.message} ${'$'}context") }
    override fun breadcrumb(message: String, data: Map<String, String>) { println("[Breadcrumb] ${'$'}message ${'$'}data") }
}
