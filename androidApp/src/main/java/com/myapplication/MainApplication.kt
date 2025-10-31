package com.myapplication

import android.app.Application

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Sentry desactivado por defecto para compilar rápido. Puede activarse con ENABLE_SENTRY.
        if (BuildConfig.ENABLE_SENTRY && BuildConfig.SENTRY_DSN.isNotBlank()) {
            // TODO: Inicializar Sentry cuando se habilite el flag.
        }
    }
}
