package aifactory.telemetry

actual fun createCrashReporter(): CrashReporter = DesktopCrashReporter()
