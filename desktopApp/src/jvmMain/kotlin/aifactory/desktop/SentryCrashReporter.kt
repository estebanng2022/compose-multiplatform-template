package aifactory.desktop

import aifactory.platform.CrashReporter

class SentryCrashReporter(private val dsn: String): CrashReporter {
    override fun init() {
        io.sentry.Sentry.init { options ->
            options.dsn = dsn
        }
    }
    override fun capture(e: Throwable, context: Map<String, String>) {
        io.sentry.Sentry.withScope { scope ->
            context.forEach { (k,v) -> scope.setTag(k, v) }
            io.sentry.Sentry.captureException(e)
        }
    }
}
