package aifactory.core

import kotlin.random.Random

/**
 * A utility for creating unique identifiers.
 * This is a basic implementation and can be replaced with a more robust UUID library if needed.
 */
object Id {
    private const val ALPHANUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun newId(prefix: String = "id", length: Int = 16): String {
        val randomPart = (1..length)
            .map { ALPHANUMERIC.random() }
            .joinToString("")
        return "${prefix}_$randomPart"
    }
}
