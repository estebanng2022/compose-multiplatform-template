package aifactory.core

import aifactory.core.health.HealthCheck
import aifactory.core.health.HealthStatus
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class App : Application() {

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        runHealthCheck()
    }

    private fun runHealthCheck() {
        appScope.launch(Dispatchers.IO) {
            Logx.i(msg = "Running startup health check...")
            val report = HealthCheck.run(applicationContext)
            val overallStatus = HealthCheck.summarize(report)

            report.items.forEach { item ->
                val logMessage = "Health Check [${item.label}]: ${item.status} - ${item.detail ?: "No details"}"
                when (item.status) {
                    HealthStatus.PASS -> Logx.i(msg = logMessage)
                    HealthStatus.WARN -> Logx.w(msg = logMessage)
                    HealthStatus.FAIL -> Logx.e(msg = logMessage)
                }
            }

            if (overallStatus == HealthStatus.FAIL) {
                Logx.e(tag = "HEALTH_CHECK_FAILURE", msg = "Critical health checks failed. The app might be unstable.")
            }
        }
    }
}
