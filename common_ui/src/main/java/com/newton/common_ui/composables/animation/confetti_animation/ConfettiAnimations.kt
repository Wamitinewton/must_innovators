package com.newton.common_ui.composables.animation.confetti_animation

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


@Composable
fun ConfettiCelebration(
    secondary: Color = MaterialTheme.colorScheme.secondary
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                color = secondary.copy(alpha = 0.15f),
                radius = size.minDimension / 3.5f,
                center = Offset(center.x + 20f, center.y - 20f)
            )
        }

        DenseConfettiAnimation(
            modifier = Modifier.fillMaxSize(),
            particleCount = 60
        )
    }
}

@Composable
fun GlobalConfettiEffect(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        SparseConfettiAnimation(
            modifier = Modifier.fillMaxSize(),
            particleCount = 80,
            animationDuration = 5000
        )
    }
}

@Composable
fun DenseConfettiAnimation(
    modifier: Modifier = Modifier,
    particleCount: Int = 60
) {
    val infiniteTransition = rememberInfiniteTransition(label = "denseConfettiAnimation")

    val confettiPieces = remember {
        List(particleCount) {
            val randomDistance = Random.nextFloat() * 120f
            val randomAngle = Random.nextFloat() * 2 * Math.PI.toFloat()

            val x = cos(randomAngle) * randomDistance
            val y = sin(randomAngle) * randomDistance

            ConfettiPiece(
                color = getConfettiColor(it % 8),
                initialPosition = Offset(x, y - 50f),
                size = Random.nextFloat() * 14f + 6f,
                angle = Random.nextFloat() * 360f,
                speedMultiplier = Random.nextFloat() * 0.7f + 0.4f,
                rotationSpeed = Random.nextFloat() * 15f + 5f,
                shape = it % 5
            )
        }
    }

    val mainAnimationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "confettiFall"
    )

    val secondaryWaveProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "confettiWave"
    )

    Canvas(modifier = modifier) {
        confettiPieces.forEach { piece ->
            val progress = (mainAnimationProgress + piece.speedMultiplier) % 1f

            val radius = 100f * (1f - progress * 0.3f)
            val waveAngle = secondaryWaveProgress * 2 * Math.PI.toFloat() * 2
            val waveAmplitude = 15f * sin(progress * 10)

            val angle = progress * 2 * Math.PI.toFloat() * piece.rotationSpeed / 10f
            val spiralX = cos(angle) * radius * piece.speedMultiplier
            val spiralY = sin(angle) * radius * piece.speedMultiplier

            val waveX = cos(waveAngle) * waveAmplitude * piece.speedMultiplier
            val waveY = sin(waveAngle) * waveAmplitude * piece.speedMultiplier

            val position = Offset(
                x = center.x + piece.initialPosition.x + spiralX + waveX,
                y = center.y + piece.initialPosition.y + spiralY + waveY
            )

            rotate(piece.angle + progress * 360 * piece.rotationSpeed, pivot = position) {
                val alpha = if (progress > 0.8f) 1f - (progress - 0.8f) * 5f else 0.9f
                drawConfettiShape(
                    position = position,
                    size = piece.size,
                    shape = piece.shape,
                    color = piece.color,
                    alpha = alpha
                )
            }
        }
    }
}

@Composable
fun SparseConfettiAnimation(
    modifier: Modifier = Modifier,
    particleCount: Int = 80,
    animationDuration: Int = 5000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sparseConfettiAnimation")

    val confettiPieces = remember {
        List(particleCount) {
            ConfettiPiece(
                color = getConfettiColor(it % 8),
                initialPosition = Offset(
                    x = Random.nextFloat() * 1000f - 200f, // Wider distribution
                    y = -Random.nextFloat() * 800f - 100f  // Start well above screen
                ),
                size = Random.nextFloat() * 16f + 5f,
                angle = Random.nextFloat() * 360f,
                speedMultiplier = Random.nextFloat() * 0.6f + 0.4f,
                rotationSpeed = Random.nextFloat() * 10f + 2f,
                shape = it % 5
            )
        }
    }

    val fallAnimationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "confettiFall"
    )

    Canvas(modifier = modifier) {
        confettiPieces.forEach { piece ->
            val yProgress = (fallAnimationProgress + piece.speedMultiplier) % 1f
            val swayAmount = 60f * piece.speedMultiplier

            val xOffset = sin(yProgress * 5) * swayAmount

            val position = Offset(
                x = piece.initialPosition.x + xOffset,
                y = piece.initialPosition.y + yProgress * (size.height + 900f)
            )

            if (position.y >= -50f && position.y <= size.height + 50f &&
                position.x >= -50f && position.x <= size.width + 50f) {

                rotate(piece.angle + yProgress * 360 * piece.rotationSpeed, pivot = position) {
                    drawConfettiShape(
                        position = position,
                        size = piece.size,
                        shape = piece.shape,
                        color = piece.color,
                        alpha = 0.9f
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawConfettiShape(
    position: Offset,
    size: Float,
    shape: Int,
    color: Color,
    alpha: Float = 1f
) {
    when (shape) {
        0 -> {
            drawRect(
                color = color,
                alpha = alpha,
                topLeft = Offset(position.x - size/2, position.y - size/4),
                size = androidx.compose.ui.geometry.Size(size, size/2)
            )
        }
        1 -> {
            drawCircle(
                color = color,
                alpha = alpha,
                radius = size/2,
                center = position
            )
        }
        2 -> {
            val path = Path().apply {
                moveTo(position.x, position.y - size/2)
                lineTo(position.x + size/2, position.y + size/2)
                lineTo(position.x - size/2, position.y + size/2)
                close()
            }
            drawPath(path, color, alpha = alpha)
        }
        3 -> {
            val path = Path().apply {
                val outerRadius = size/2
                val innerRadius = size/4

                for (i in 0 until 5) {
                    val outerAngle = Math.PI.toFloat() / 5 * (i * 2) - Math.PI.toFloat() / 2
                    val innerAngle = Math.PI.toFloat() / 5 * (i * 2 + 1) - Math.PI.toFloat() / 2

                    val outerX = position.x + cos(outerAngle) * outerRadius
                    val outerY = position.y + sin(outerAngle) * outerRadius

                    val innerX = position.x + cos(innerAngle) * innerRadius
                    val innerY = position.y + sin(innerAngle) * innerRadius

                    if (i == 0) moveTo(outerX, outerY) else lineTo(outerX, outerY)
                    lineTo(innerX, innerY)
                }
                close()
            }
            drawPath(path, color, alpha = alpha)
        }
        else -> {
            val path = Path().apply {
                moveTo(position.x, position.y - size/2)
                lineTo(position.x + size/2, position.y)
                lineTo(position.x, position.y + size/2)
                lineTo(position.x - size/2, position.y)
                close()
            }
            drawPath(path, color, alpha = alpha)
        }
    }
}

private fun getConfettiColor(index: Int): Color {
    return when (index) {
        0 -> Color(0xFF4285F4)
        1 -> Color(0xFFEA4335)
        2 -> Color(0xFFFBBC04)
        3 -> Color(0xFF34A853)
        4 -> Color(0xFFFF6D00)
        5 -> Color(0xFF9C27B0)
        6 -> Color(0xFF00BCD4)
        7 -> Color(0xFFFFEB3B)
        else -> Color(0xFFE91E63)
    }
}



class DottedShape(val step: Dp) : androidx.compose.ui.graphics.Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): androidx.compose.ui.graphics.Outline {
        val stepPx = with(density) { step.toPx() }
        val path = Path().apply {
            var currentX = 0f
            val width = size.width
            val height = size.height

            while (currentX < width) {
                moveTo(currentX, 0f)
                lineTo(currentX + stepPx / 2, height)
                currentX += stepPx
            }
        }
        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}