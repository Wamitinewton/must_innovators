package com.newton.core.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

/**
 * Generates a deterministic color based on the name.
 * This ensures the same name always gets the same color.
 */
fun generateColorFromName(name: String): Color {
    // Calculate a hash value for the name
    val hash = name.hashCode()

    // Create separate components from different parts of the hash
    // to ensure colors are more diverse
    val r = (abs(hash) % 200 + 30) / 255f         // Range 30-230 for red
    val g = (abs(hash / 256) % 200 + 30) / 255f   // Range 30-230 for green
    val b = (abs(hash / 65536) % 200 + 30) / 255f // Range 30-230 for blue

    // Ensure color is vibrant enough - adjust saturation if needed
    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val saturation = max - min

    // If saturation is too low, boost the dominant color
    return if (saturation < 0.3f) {
        when {
            r >= g && r >= b -> Color(0.8f, g * 0.7f, b * 0.7f) // Red dominant
            g >= r && g >= b -> Color(r * 0.7f, 0.8f, b * 0.7f) // Green dominant
            else -> Color(r * 0.7f, g * 0.7f, 0.8f)            // Blue dominant
        }
    } else {
        Color(r, g, b)
    }
}

