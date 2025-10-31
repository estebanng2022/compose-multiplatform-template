package aifactory

import android.app.Application
import com.myapplication.BuildConfig

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.ENABLE_SENTRY && BuildConfig.SENTRY_DSN.isNotBlank()) {
            // Sentry disabled by default. Initialize here when enabled.
        }
    }
}
