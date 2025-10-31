package aifactory.presentation.validation

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

data class ThemeValidationReport(val passed: Boolean, val issues: List<String>)

object ThemeValidator {
    fun validateYaml(content: String): ThemeValidationReport {
        val map = parseSimpleYaml(content)
        val issues = mutableListOf<String>()

        fun colorHex(key: String): String? = (map[key] as? String)?.takeIf { it.startsWith("#") && (it.length == 7 || it.length == 4) }

        val primary = colorHex("primary") ?: run { issues += "Missing or invalid primary color"; null }
        val background = colorHex("background") ?: run { issues += "Missing or invalid background color"; null }
        val secondary = colorHex("secondary")

        if (primary != null && background != null) {
            val ratio = contrastRatio(primary, background)
            if (ratio < 4.5) issues += "Insufficient contrast primary/background: $ratio (expected ≥ 4.5)"
        }
        if (secondary != null && background != null) {
            val ratio = contrastRatio(secondary, background)
            if (ratio < 3.0) issues += "Low contrast secondary/background: $ratio (expected ≥ 3.0)"
        }

        return ThemeValidationReport(passed = issues.isEmpty(), issues = issues)
    }

    private fun parseSimpleYaml(content: String): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        content.lineSequence().forEach { raw ->
            val line = raw.trim()
            if (line.isEmpty() || line.startsWith("#")) return@forEach
            val idx = line.indexOf(":")
            if (idx > 0) {
                val key = line.substring(0, idx).trim()
                val value = line.substring(idx + 1).trim().trim('"')
                map[key] = value
            }
        }
        return map
    }

    private fun contrastRatio(hex1: String, hex2: String): Double {
        fun toRGB(hex: String): Triple<Double, Double, Double> {
            val h = if (hex.length == 4) {
                "#" + hex[1] + hex[1] + hex[2] + hex[2] + hex[3] + hex[3]
            } else hex
            val r = h.substring(1, 3).toInt(16) / 255.0
            val g = h.substring(3, 5).toInt(16) / 255.0
            val b = h.substring(5, 7).toInt(16) / 255.0
            return Triple(r, g, b)
        }
        fun linearize(c: Double): Double = if (c <= 0.03928) c / 12.92 else ((c + 0.055) / 1.055).pow(2.4)
        fun luminance(hex: String): Double {
            val (r, g, b) = toRGB(hex)
            val rl = linearize(r)
            val gl = linearize(g)
            val bl = linearize(b)
            return 0.2126 * rl + 0.7152 * gl + 0.0722 * bl
        }
        val l1 = luminance(hex1)
        val l2 = luminance(hex2)
        val (a, b) = max(l1, l2) to min(l1, l2)
        return (a + 0.05) / (b + 0.05)
    }
}
