package aifactory.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ThemeTokens(
    val primary: String,
    val secondary: String,
    val background: String,
    val surface: String,
    val success: String? = null,
    val warning: String? = null,
    val error: String? = null,
    val typography: TypographySpec? = null,
    val shapes: ShapesSpec? = null,
    val spacing: SpacingSpec? = null,
    val elevation: ElevationSpec? = null,
    val a11y: A11ySpec? = null,
)

@Serializable
data class TypographySpec(
    val family: String? = null,
    val scale: String? = null,
    val weight: WeightSpec? = null,
)

@Serializable
data class WeightSpec(
    val regular: Int? = null,
    val medium: Int? = null,
    val semibold: Int? = null,
    val bold: Int? = null,
)

@Serializable
data class ShapesSpec(
    val radius: String? = null,
)

@Serializable
data class SpacingSpec(
    val base: Int? = null,
)

@Serializable
data class ElevationSpec(
    val level: String? = null,
)

@Serializable
data class A11ySpec(
    val contrast: String? = null,
)

