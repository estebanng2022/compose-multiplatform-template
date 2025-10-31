package aifactory.platform

interface CrashReporter {
    fun init()
    fun capture(e: Throwable, context: Map<String, String> = emptyMap())
}
