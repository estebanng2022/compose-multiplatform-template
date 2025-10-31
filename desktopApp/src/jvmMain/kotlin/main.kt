import aifactory.core.flags.DesktopFeatureFlags
import aifactory.core.flags.LocalFeatureFlags
import aifactory.platform.AppPaths
import aifactory.ui.widgets.AppScaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.rememberNavController
import io.sentry.Sentry

fun main() = application {
    val flags = DesktopFeatureFlags()
    
    // Initialize Sentry for Desktop
    val sentryDsn = System.getenv("SENTRY_DSN_DESKTOP") ?: ""
    if (sentryDsn.isNotBlank()) {
        Sentry.init { options ->
            options.dsn = sentryDsn
            options.environment = if (flags.isWireframe) "wireframes" else "prod"
            val version = System.getProperty("app.version") ?: "1.0.0"
            options.release = "desktop@$version"
            options.beforeSend = { event, _ ->
                event.request = null
                event.user = null
                event
            }
        }
        Sentry.setTag("platform", "desktop")
        Sentry.setTag("wireframe", flags.isWireframe.toString())
        Sentry.setTag("mockData", flags.useMockData.toString())
    }

    System.setProperty("app.version", "1.0.0") // Set version for Sentry

    Window(
        onCloseRequest = ::exitApplication,
        title = "AiFactory Desktop"
    ) {
        CompositionLocalProvider(LocalFeatureFlags provides flags) {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppScaffold(navController = navController)
                }
            }
        }
    }
}
