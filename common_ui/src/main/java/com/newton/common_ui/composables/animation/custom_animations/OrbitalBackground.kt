package com.newton.common_ui.composables.animation.custom_animations

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun OrbitalsBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = Color(0xFF4CAF50),
    secondaryColor: Color = Color(0xFF2196F3),
    tertiaryColor: Color = Color(0xFFFF9800),
    primaryAlpha: Float = 0.25f,
    secondaryAlpha: Float = 0.2f,
    tertiaryAlpha: Float = 0.25f,
    backgroundAlpha: Float = 0.7f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orbit-animation")

    var isAnimating by remember { mutableStateOf(true) }

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            isAnimating = when (event) {
                Lifecycle.Event.ON_RESUME -> true
                Lifecycle.Event.ON_PAUSE -> false
                else -> isAnimating
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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

    val orbitPulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orbitPulse"
    )

    val dashPattern1 = remember { floatArrayOf(20f, 10f) }
    val dashPattern2 = remember { floatArrayOf(15f, 15f) }

    val primaryWithAlpha = remember(primaryColor, primaryAlpha) { primaryColor.copy(alpha = primaryAlpha) }
    val secondaryWithAlpha = remember(secondaryColor, secondaryAlpha) { secondaryColor.copy(alpha = secondaryAlpha) }
    val tertiaryWithAlpha = remember(tertiaryColor, tertiaryAlpha) { tertiaryColor.copy(alpha = tertiaryAlpha) }
    val waveFillColor = remember(secondaryColor) { secondaryColor.copy(alpha = 0.05f) }
    val pointColor = remember(primaryColor) { primaryColor.copy(alpha = 0.3f) }
    val circleColor = remember(primaryColor) { primaryColor.copy(alpha = 0.07f) }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .alpha(backgroundAlpha)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val center = Offset(canvasWidth / 2, canvasHeight / 2)

        val orbit1RadiusX = canvasWidth * 0.4f * orbitPulse
        val orbit1RadiusY = canvasHeight * 0.35f
        val orbit2RadiusX = canvasWidth * 0.5f
        val orbit2RadiusY = canvasHeight * 0.25f * orbitPulse
        val orbit3RadiusX = canvasWidth * 0.6f * orbitPulse
        val orbit3RadiusY = canvasHeight * 0.5f * orbitPulse
        val orbit3Center = Offset(canvasWidth * 0.6f, canvasHeight * 0.7f)
        val circleRadius = canvasWidth * 0.2f * orbitPulse
        val circleCenter = Offset(canvasWidth * 0.15f, canvasHeight * 0.2f)

        drawOrbitalEllipse(
            center = center,
            radiusX = orbit1RadiusX,
            radiusY = orbit1RadiusY,
            rotation = rotation1,
            color = primaryWithAlpha,
            strokeWidth = 1.5f,
            dashPattern = dashPattern1
        )

        drawOrbitalEllipse(
            center = center,
            radiusX = orbit2RadiusX,
            radiusY = orbit2RadiusY,
            rotation = rotation2,
            color = secondaryWithAlpha,
            strokeWidth = 3.0f,
            dashPattern = dashPattern1
        )

        drawOrbitalEllipse(
            center = orbit3Center,
            radiusX = orbit3RadiusX,
            radiusY = orbit3RadiusY,
            rotation = rotation1 * 0.7f,
            color = tertiaryWithAlpha,
            strokeWidth = 1.2f,
            dashPattern = dashPattern1
        )

        drawCircle(
            color = circleColor,
            radius = circleRadius,
            center = circleCenter,
            style = Stroke(
                width = 1.5f,
                pathEffect = PathEffect.dashPathEffect(dashPattern2, 0f)
            )
        )

        val pointsCount = 5
        for (i in 0 until pointsCount) {
            val angle = (i * 2 * PI / pointsCount + rotation1 * PI / 180).toFloat()
            val x = center.x + cos(angle) * orbit1RadiusX
            val y = center.y + sin(angle) * orbit1RadiusY
            drawCircle(
                color = pointColor,
                radius = 3f,
                center = Offset(x, y)
            )
        }

        val wavePath = Path().apply {
            moveTo(0f, canvasHeight * 0.5f)
            for (i in 0..canvasWidth.toInt() step 100) {
                val x = i.toFloat()
                val y = canvasHeight * 0.5f + sin((x + rotation1) * PI / 180 * 2) * 40
                lineTo(x, y.toFloat())
            }
        }

        drawPath(
            path = wavePath,
            color = waveFillColor,
            style = Stroke(
                width = 2f,
                cap = StrokeCap.Round
            )
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawOrbitalEllipse(
    center: Offset,
    radiusX: Float,
    radiusY: Float,
    rotation: Float,
    color: Color,
    strokeWidth: Float = 1.5f,
    dashPattern: FloatArray
) {
    rotate(degrees = rotation, pivot = center) {
        drawOval(
            color = color,
            topLeft = Offset(center.x - radiusX, center.y - radiusY),
            size = Size(radiusX * 2, radiusY * 2),
            style = Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(dashPattern, phase = rotation % 30)
            )
        )
    }
}