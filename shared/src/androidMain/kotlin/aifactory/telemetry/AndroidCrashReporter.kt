package aifactory.telemetry
import io.sentry.Sentry
import io.sentry.Breadcrumb

class AndroidCrashReporter : CrashReporter {
    override fun init() { /* Initialized in MainApplication */ }
    override fun capture(e: Throwable, context: Map<String, String>) {
        Sentry.withScope { s ->
            context.forEach { (k, v) -> s.setTag(k, v) }
            Sentry.captureException(e)
        }
    }
    override fun breadcrumb(message: String, data: Map<String, String>) {
        val bc = Breadcrumb().apply { 
            this.message = message
            data.forEach { (k, v) -> setData(k, v) } 
        }
        Sentry.addBreadcrumb(bc)
    }
}
