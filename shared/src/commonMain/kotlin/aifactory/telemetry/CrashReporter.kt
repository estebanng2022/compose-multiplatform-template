package aifactory.telemetry

interface CrashReporter {
    fun init()
    fun capture(e: Throwable, context: Map<String, String> = emptyMap())
    fun breadcrumb(message: String, data: Map<String, String> = emptyMap())
}
