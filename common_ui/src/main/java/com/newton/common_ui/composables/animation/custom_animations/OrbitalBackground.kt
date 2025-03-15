package com.newton.common_ui.composables.animation.custom_animations

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun OrbitalsBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = Color(0xFF4CAF50).copy(alpha = 0.25f),
    secondaryColor: Color = Color(0xFF2196F3).copy(alpha = 0.2f),
    tertiaryColor: Color = Color(0xFFFF9800).copy(alpha = 0.25f),

    ) {
    val infiniteTransition = rememberInfiniteTransition(label = "orbit-animation")

    // Animations for different orbital movements
    val rotation1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation1"
    )

    val rotation2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(45000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation2"
    )

    // Pulses for orbit sizes
    val orbitPulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orbitPulse"
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .alpha(0.7f)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val center = Offset(canvasWidth / 2, canvasHeight / 2)

        // Draw elliptical orbits
        drawOrbitalEllipse(
            center = center,
            radiusX = canvasWidth * 0.4f * orbitPulse,
            radiusY = canvasHeight * 0.35f,
            rotation = rotation1,
            color = primaryColor,
            strokeWidth = 1.5f
        )

        drawOrbitalEllipse(
            center = center,
            radiusX = canvasWidth * 0.5f,
            radiusY = canvasHeight * 0.25f * orbitPulse,
            rotation = rotation2,
            color = secondaryColor,
            strokeWidth = 3.0f
        )

        // Draw a larger orbit
        drawOrbitalEllipse(
            center = Offset(canvasWidth * 0.6f, canvasHeight * 0.7f),
            radiusX = canvasWidth * 0.6f * orbitPulse,
            radiusY = canvasHeight * 0.5f * orbitPulse,
            rotation = rotation1 * 0.7f,
            color = tertiaryColor,
            strokeWidth = 1.2f
        )

        // Draw some circular orbits
        drawCircle(
            color = primaryColor.copy(alpha = 0.07f),
            radius = canvasWidth * 0.2f * orbitPulse,
            center = Offset(canvasWidth * 0.15f, canvasHeight * 0.2f),
            style = Stroke(
                width = 1.5f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
            )
        )

        // Draw some small points on the orbits
        val pointsCount = 5
        for (i in 0 until pointsCount) {
            val angle = (i * 2 * PI / pointsCount + rotation1 * PI / 180).toFloat()
            val x = center.x + cos(angle) * canvasWidth * 0.4f * orbitPulse
            val y = center.y + sin(angle) * canvasHeight * 0.35f
            drawCircle(
                color = primaryColor.copy(alpha = 0.3f),
                radius = 3f,
                center = Offset(x, y)
            )
        }

        // Draw a subtle wave path
        val wavePath = Path().apply {
            moveTo(0f, canvasHeight * 0.5f)
            for (i in 0..canvasWidth.toInt() step 50) {
                val x = i.toFloat()
                val y = canvasHeight * 0.5f + sin((x + rotation1) * PI / 180 * 2) * 40
                lineTo(x, y.toFloat())
            }
        }

        drawPath(
            path = wavePath,
            color = secondaryColor.copy(alpha = 0.05f),
            style = Stroke(
                width = 2f,
                cap = StrokeCap.Round
            )
        )
    }
}

// Helper function to draw elliptical orbits
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawOrbitalEllipse(
    center: Offset,
    radiusX: Float,
    radiusY: Float,
    rotation: Float,
    color: Color,
    strokeWidth: Float = 1.5f
) {
    rotate(degrees = rotation, pivot = center) {
        drawOval(
            color = color,
            topLeft = Offset(center.x - radiusX, center.y - radiusY),
            size = Size(radiusX * 2, radiusY * 2),
            style = Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), phase = rotation % 30)
            )
        )
    }
}