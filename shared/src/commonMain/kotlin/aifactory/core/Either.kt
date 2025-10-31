package aifactory.core

/**
 * Represents a value of one of two possible types (a disjoint union).
 * An instance of Either is either an instance of [Left] or [Right].
 *
 * By convention, [Left] is used for failure and [Right] is used for success.
 */
sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()
    data class Right<out R>(val value: R) : Either<Nothing, R>()
}
