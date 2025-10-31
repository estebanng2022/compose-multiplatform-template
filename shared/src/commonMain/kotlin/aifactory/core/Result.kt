package aifactory.core

/**
 * A discriminated union that encapsulates a successful result with a value of type [T]
 * or a failure with an arbitrary [Throwable].
 */
sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()

    val isSuccess get() = this is Success
    val isFailure get() = this is Failure

    fun getOrNull(): T? = if (this is Success) value else null

    fun exceptionOrNull(): Throwable? = if (this is Failure) exception else null
}

inline fun <T> runCatching(block: () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: Throwable) {
        Result.Failure(e)
    }
}
