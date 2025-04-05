package com.newton.commonUi.composables.animation.customAnimations

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*

fun Modifier.shimmerEffect(
    colors: List<Color> =
        listOf(
            Color(0xFF222222),
            Color(0xFF3D3D3D),
            Color(0xFF4D4D4D),
            Color(0xFF3D3D3D),
            Color(0xFF222222)
        ),
    durationMillis: Int = 1400
): Modifier =
    composed {
        var size by remember { mutableStateOf(IntSize.Zero) }
        val transition = rememberInfiniteTransition(label = "shimmer_effect")
        val startOffsetX by transition.animateFloat(
            initialValue = -2 * size.width.toFloat(),
            targetValue = 2 * size.width.toFloat(),
            animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmer_offset_x"
        )

        background(
            brush =
            Brush.linearGradient(
                colors = colors,
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            )
        ).onGloballyPositioned {
            size = it.size
        }
    }

@Composable
fun ShimmerWithFade(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1500,
    colors: List<Color> =
        listOf(
            Color(0xFF222222),
            Color(0xFF3D3D3D),
            Color(0xFF4D4D4D),
            Color(0xFF3D3D3D),
            Color(0xFF222222)
        ),
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        content()

        val transition = rememberInfiniteTransition(label = "shimmer_fade")
        val alpha by transition.animateFloat(
            initialValue = 0.6f,
            targetValue = 1f,
            animationSpec =
            infiniteRepeatable(
                animation =
                tween(
                    durationMillis = durationMillis / 2,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "shimmer_fade_alpha"
        )

        Box(
            modifier =
            Modifier
                .matchParentSize()
                .alpha(alpha)
                .shimmerEffect(colors = colors)
        )
    }
}
