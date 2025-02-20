package com.newton.common_ui.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.coroutines.delay

/**
 * A reusable composable that adds a colorful glowing border animation effect to its content.
 * Perfect for success/achievement notifications across the app.
 *
 * @param isActive Whether the glow animation should be active
 * @param colors List of colors to use in the gradient. Defaults to a success-themed gradient
 * @param strokeWidth Maximum width of the border in pixels. Defaults to 20f
 * @param animationDuration Duration of the animation in milliseconds. Defaults to 800
 * @param onAnimationComplete Callback triggered when animation completes
 * @param content The content to be wrapped with the glow effect
 */

@Composable
fun GlowingBorderAnimation(
    isActive: Boolean,
    colors: List<Pair<Float, Color>> = listOf(
        0.0f to Color(0xFF4CAF50), // Green
        0.3f to Color(0xFF2196F3), // Blue
        0.6f to Color(0xFFE91E63), // Pink
        1f to Color(0xFFFFEB3B)
    ),
    strokeWidth: Float = 20f,
    animationDuration: Int = 800,
    delayBeforeCompletion: Long = 1000,
    onAnimationComplete: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val animationBorderWidth = remember { Animatable(0f) }
    var animationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(isActive) {
        if (isActive && !animationStarted) {
            animationStarted = true
            animationBorderWidth.animateTo(
                targetValue = strokeWidth,
                animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing)
            )
            delay(delayBeforeCompletion)
            onAnimationComplete()
        }
    }

    LaunchedEffect(isActive) {
        if (!isActive) {
            animationStarted = false
            animationBorderWidth.snapTo(0f)
        }
    }
    Box(
        modifier = Modifier.drawBehind {
            if (isActive) {
                val colorStops = colors.toTypedArray()

                drawRoundRect(
                    brush = Brush.linearGradient(
                        colorStops = colorStops,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height)
                    ),
                    style = Stroke(
                        width = animationBorderWidth.value,
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    ) {
        content()
    }
}


/**
 * Color scheme presets for the GlowingBorderAnimation
 */
object GlowingBorderPresets {
    val Success = listOf(
        0.0f to Color(0xFF4CAF50),  // Green
        0.3f to Color(0xFF2196F3),  // Blue
        0.6f to Color(0xFFE91E63),  // Pink
        1f to Color(0xFFFFEB3B)     // Yellow
    )

    val Error = listOf(
        0.0f to Color(0xFFF44336),  // Red
        0.3f to Color(0xFFFF9800),  // Orange
        0.6f to Color(0xFFFFEB3B),  // Yellow
        1f to Color(0xFFF44336)     // Red
    )

    val Info = listOf(
        0.0f to Color(0xFF2196F3),  // Blue
        0.3f to Color(0xFF00BCD4),  // Cyan
        0.6f to Color(0xFF3F51B5),  // Indigo
        1f to Color(0xFF9C27B0)     // Purple
    )

    val Premium = listOf(
        0.0f to Color(0xFFFFD700),  // Gold
        0.3f to Color(0xFFFFA500),  // Orange
        0.6f to Color(0xFFDAA520),  // Golden Rod
        1f to Color(0xFFFFD700)     // Gold
    )
}