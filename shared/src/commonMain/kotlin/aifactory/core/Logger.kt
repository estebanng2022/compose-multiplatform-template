package aifactory.core

enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}

interface Logger {
    fun log(level: LogLevel, message: String, throwable: Throwable? = null)
}

fun Logger.d(message: String) = log(LogLevel.DEBUG, message)
fun Logger.i(message: String) = log(LogLevel.INFO, message)
fun Logger.w(message: String, throwable: Throwable? = null) = log(LogLevel.WARN, message, throwable)
fun Logger.e(message: String, throwable: Throwable? = null) = log(LogLevel.ERROR, message, throwable)
