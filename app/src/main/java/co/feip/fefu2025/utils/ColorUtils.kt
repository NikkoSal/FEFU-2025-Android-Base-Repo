package co.feip.fefu2025.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

fun genreNameToColor(genreName: String): Color {
    val hash = genreName.hashCode()
    val hue = abs(hash % 360).toFloat()
    val saturation = 0.7f
    val value = 0.8f
    return Color.hsv(hue, saturation, value)
}