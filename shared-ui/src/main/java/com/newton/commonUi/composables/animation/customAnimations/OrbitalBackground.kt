package com.newton.commonUi.composables.animation.customAnimations

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.*
import androidx.lifecycle.*
import kotlin.math.*
import kotlin.random.*

/**
 *
 * @param modifier Modifier to be applied to the canvas
 * @param primaryColor Primary color for orbital patterns
 * @param secondaryColor Secondary color for orbital patterns and wave effects
 * @param tertiaryColor Tertiary color for additional orbital patterns
 * @param primaryAlpha Alpha value for primary color elements
 * @param secondaryAlpha Alpha value for secondary color elements
 * @param tertiaryAlpha Alpha value for tertiary color elements
 * @param backgroundAlpha Global alpha value for the entire background
 * @param showStars Whether to show animated stars in the background
 */
@Composable
fun OrbitalsBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    secondaryColor: Color = MaterialTheme.colorScheme.secondary,
    tertiaryColor: Color = MaterialTheme.colorScheme.tertiary,
    primaryAlpha: Float = 0.15f, // Reduced from 0.35f
    secondaryAlpha: Float = 0.12f, // Reduced from 0.25f
    tertiaryAlpha: Float = 0.1f, // Reduced from 0.3f
    backgroundAlpha: Float = 0.7f, // Reduced from 1f
    showStars: Boolean = true,
    starDensity: Float = 0.5f // New parameter to control star density
) {
    val infiniteTransition = rememberInfiniteTransition(label = "space-animation")

    var isAnimating by remember { mutableStateOf(true) }

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                isAnimating =
                    when (event) {
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
        animationSpec =
        infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing), // Slowed down from 40000
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation1"
    )

    val rotation2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(80000, easing = LinearEasing), // Slowed down from 60000
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation2"
    )

    val orbitPulse by infiniteTransition.animateFloat(
        initialValue = 0.95f, // Reduced pulse range from 0.85f
        targetValue = 1.05f, // Reduced pulse range from 1.15f
        animationSpec =
        infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing), // Slowed down from 8000
            repeatMode = RepeatMode.Reverse
        ),
        label = "orbitPulse"
    )

    val starAlpha1 by infiniteTransition.animateFloat(
        initialValue = 0.05f, // Reduced from 0.1f
        targetValue = 0.4f, // Reduced from 0.9f
        animationSpec =
        infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing), // Slowed down from 2000
            repeatMode = RepeatMode.Reverse
        ),
        label = "starAlpha1"
    )

    val starAlpha2 by infiniteTransition.animateFloat(
        initialValue = 0.1f, // Reduced from 0.2f
        targetValue = 0.3f, // Reduced from 0.7f
        animationSpec =
        infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing), // Slowed down from 3000
            repeatMode = RepeatMode.Reverse
        ),
        label = "starAlpha2"
    )

    val starAlpha3 by infiniteTransition.animateFloat(
        initialValue = 0.02f, // Reduced from 0.05f
        targetValue = 0.25f, // Reduced from 0.6f
        animationSpec =
        infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing), // Slowed down from 4000
            repeatMode = RepeatMode.Reverse
        ),
        label = "starAlpha3"
    )

    val starRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f, // Reduced from 45f for subtler rotation
        animationSpec =
        infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing), // Slowed down from 10000
            repeatMode = RepeatMode.Reverse
        ),
        label = "starRotation"
    )

    val dashPattern1 = remember { floatArrayOf(15f, 15f) } // Adjusted for subtler pattern
    val dashPattern2 = remember { floatArrayOf(10f, 20f) } // Adjusted for subtler pattern
    val dashPattern3 = remember { floatArrayOf(5f, 15f) } // Adjusted for subtler pattern

    // Apply star density factor to reduce star counts
    val smallStarCount = remember { (60 * starDensity).toInt() }
    val mediumStarCount = remember { (40 * starDensity).toInt() }
    val largeStarCount = remember { (20 * starDensity).toInt() }
    val extraLargeStarCount = remember { (8 * starDensity).toInt() }

    val smallStars =
        remember {
            List(smallStarCount) {
                Star(
                    x = Random.nextFloat(),
                    y = Random.nextFloat(),
                    size = Random.nextFloat() * 0.8f + 0.5f, // Reduced size
                    alphaGroup = Random.nextInt(3) + 1,
                    pointCount = 5,
                    rotation = Random.nextFloat() * 360f
                )
            }
        }

    val mediumStars =
        remember {
            List(mediumStarCount) {
                Star(
                    x = Random.nextFloat(),
                    y = Random.nextFloat(),
                    size = Random.nextFloat() * 1.2f + 1.0f, // Reduced from 1.8f + 1.4f
                    alphaGroup = Random.nextInt(3) + 1,
                    pointCount = Random.nextInt(4, 7),
                    rotation = Random.nextFloat() * 360f
                )
            }
        }

    val largeStars =
        remember {
            List(largeStarCount) {
                Star(
                    x = Random.nextFloat(),
                    y = Random.nextFloat(),
                    size = Random.nextFloat() * 1.5f + 1.5f, // Reduced from 2.5f + 2.0f
                    alphaGroup = Random.nextInt(3) + 1,
                    pointCount = Random.nextInt(5, 8),
                    rotation = Random.nextFloat() * 360f
                )
            }
        }

    val extraLargeStars =
        remember {
            List(extraLargeStarCount) {
                Star(
                    x = Random.nextFloat(),
                    y = Random.nextFloat(),
                    size = Random.nextFloat() * 2.0f + 2.0f, // Reduced from 3.5f + 3.0f
                    alphaGroup = Random.nextInt(3) + 1,
                    pointCount = Random.nextInt(6, 9),
                    rotation = Random.nextFloat() * 360f
                )
            }
        }

    // Reduced galaxy clusters from 3 to 2
    val galaxyClusters =
        remember {
            List(2) {
                GalaxyCluster(
                    centerX = Random.nextFloat() * 0.8f + 0.1f,
                    centerY = Random.nextFloat() * 0.8f + 0.1f,
                    radius = Random.nextFloat() * 0.10f + 0.03f, // Reduced from 0.15f + 0.05f
                    rotation = Random.nextFloat() * 360f,
                    starCount = Random.nextInt(10, 20) // Reduced from 15, 30
                )
            }
        }

    val primaryWithAlpha =
        remember(primaryColor, primaryAlpha) { primaryColor.copy(alpha = primaryAlpha) }
    val secondaryWithAlpha =
        remember(secondaryColor, secondaryAlpha) { secondaryColor.copy(alpha = secondaryAlpha) }
    val tertiaryWithAlpha =
        remember(tertiaryColor, tertiaryAlpha) { tertiaryColor.copy(alpha = tertiaryAlpha) }
    val waveFillColor =
        remember(secondaryColor) { secondaryColor.copy(alpha = 0.03f) } // Reduced from 0.05f
    val pointColor =
        remember(primaryColor) { primaryColor.copy(alpha = 0.25f) } // Reduced from 0.5f
    val circleColor =
        remember(primaryColor) { primaryColor.copy(alpha = 0.06f) } // Reduced from 0.1f

    val starBaseColor = MaterialTheme.colorScheme.onSurface
    val starColor1 =
        remember(starBaseColor) { starBaseColor.copy(alpha = 0.4f) } // Reduced from 0.9f
    val starColor2 =
        remember(tertiaryColor) { tertiaryColor.copy(alpha = 0.3f) } // Reduced from 0.7f
    val starColor3 =
        remember(secondaryColor) { secondaryColor.copy(alpha = 0.35f) } // Reduced from 0.8f
    val brightStar = remember(primaryColor) { Color.White.copy(alpha = 0.5f) } // Reduced from 0.95f
    val blueStar = remember { Color(0xFF8EB8FF).copy(alpha = 0.4f) } // Reduced from 0.9f
    val redStar = remember { Color(0xFFFF9E9E).copy(alpha = 0.4f) } // Reduced from 0.9f

    Canvas(
        modifier =
        modifier
            .fillMaxSize()
            .alpha(backgroundAlpha)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val center = Offset(canvasWidth / 2, canvasHeight / 2)

        galaxyClusters.forEach { cluster ->
            val clusterCenterX = cluster.centerX * canvasWidth
            val clusterCenterY = cluster.centerY * canvasHeight
            val clusterCenter = Offset(clusterCenterX, clusterCenterY)
            val clusterRadius = cluster.radius * minOf(canvasWidth, canvasHeight)

            drawCircle(
                color = tertiaryColor.copy(alpha = 0.02f), // Reduced from 0.05f
                radius = clusterRadius * 1.5f,
                center = clusterCenter
            )

            for (i in 0 until cluster.starCount) {
                val angle = (i * 137.5f + cluster.rotation + rotation1 * 0.05f) % 360
                val distanceFactor = i.toFloat() / cluster.starCount
                val distance = clusterRadius * (0.2f + distanceFactor * 0.8f)
                val x = clusterCenterX + cos(angle * PI.toFloat() / 180) * distance
                val y = clusterCenterY + sin(angle * PI.toFloat() / 180) * distance

                val starSize =
                    0.8f + distanceFactor * 1.0f // Reduced from 1.0f + distanceFactor * 1.5f
                val starColor =
                    when {
                        i % 5 == 0 -> blueStar
                        i % 7 == 0 -> redStar
                        else -> starColor1
                    }.copy(alpha = 0.2f + distanceFactor * 0.3f) // Reduced from 0.3f + distanceFactor * 0.6f

                drawStar(
                    center = Offset(x, y),
                    outerRadius = starSize,
                    innerRadius = starSize * 0.4f,
                    points = 5,
                    color = starColor,
                    rotation = (angle + rotation1 * 0.1f) % 360
                )
            }
        }

        if (showStars) {
            // Draw small stars
            smallStars.forEach { star ->
                val starAlpha =
                    when (star.alphaGroup) {
                        1 -> starAlpha1
                        2 -> starAlpha2
                        else -> starAlpha3
                    }

                drawStar(
                    center = Offset(star.x * canvasWidth, star.y * canvasHeight),
                    outerRadius = star.size * 1.2f,
                    innerRadius = star.size * 0.4f,
                    points = star.pointCount,
                    color = starColor1.copy(alpha = starAlpha),
                    rotation = star.rotation + starRotation
                )
            }

            // Draw medium stars
            mediumStars.forEach { star ->
                val starAlpha =
                    when (star.alphaGroup) {
                        1 -> starAlpha2
                        2 -> starAlpha3
                        else -> starAlpha1
                    }

                drawStar(
                    center = Offset(star.x * canvasWidth, star.y * canvasHeight),
                    outerRadius = star.size * 1.2f,
                    innerRadius = star.size * 0.4f,
                    points = star.pointCount,
                    color = starColor2.copy(alpha = starAlpha),
                    rotation = star.rotation - starRotation * 0.7f
                )
            }

            // Draw large stars
            largeStars.forEach { star ->
                val starAlpha =
                    when (star.alphaGroup) {
                        1 -> starAlpha3
                        2 -> starAlpha1
                        else -> starAlpha2
                    }

                drawStar(
                    center = Offset(star.x * canvasWidth, star.y * canvasHeight),
                    outerRadius = star.size * 1.2f,
                    innerRadius = star.size * 0.4f,
                    points = star.pointCount,
                    color = starColor3.copy(alpha = starAlpha),
                    rotation = star.rotation + starRotation * 0.5f
                )

                // Add a subtle glow around large stars (reduced radius)
                drawCircle(
                    color = starColor3.copy(alpha = starAlpha * 0.15f), // Reduced from 0.3f
                    radius = star.size * 1.5f, // Reduced from 2.0f
                    center = Offset(star.x * canvasWidth, star.y * canvasHeight)
                )
            }

            // Draw extra large stars with glow effects
            extraLargeStars.forEach { star ->
                val starAlpha =
                    when (star.alphaGroup) {
                        1 -> starAlpha1 * 0.7f + 0.1f // Reduced intensity
                        2 -> starAlpha2 * 0.7f + 0.1f // Reduced intensity
                        else -> starAlpha3 * 0.7f + 0.1f // Reduced intensity
                    }

                // Draw multiple layer glows
                val starColor =
                    when (Random.nextInt(3)) {
                        0 -> blueStar
                        1 -> redStar
                        else -> brightStar
                    }

                // Outer glow
                drawCircle(
                    color = starColor.copy(alpha = starAlpha * 0.08f), // Reduced from 0.15f
                    radius = star.size * 3.0f, // Reduced from 4.0f
                    center = Offset(star.x * canvasWidth, star.y * canvasHeight)
                )

                // Middle glow
                drawCircle(
                    color = starColor.copy(alpha = starAlpha * 0.15f), // Reduced from 0.3f
                    radius = star.size * 2.0f, // Reduced from 2.5f
                    center = Offset(star.x * canvasWidth, star.y * canvasHeight)
                )

                // Inner glow
                drawCircle(
                    color = starColor.copy(alpha = starAlpha * 0.25f), // Reduced from 0.5f
                    radius = star.size * 1.2f, // Reduced from 1.5f
                    center = Offset(star.x * canvasWidth, star.y * canvasHeight)
                )

                // Star shape
                drawStar(
                    center = Offset(star.x * canvasWidth, star.y * canvasHeight),
                    outerRadius = star.size * 1.2f, // Reduced from 1.4f
                    innerRadius = star.size * 0.5f,
                    points = star.pointCount,
                    color = starColor.copy(alpha = starAlpha),
                    rotation = star.rotation + starRotation * 0.3f
                )

                // Sparkle lines for extra effect (reduced)
                val sparkleCount = 4
                for (i in 0 until sparkleCount) {
                    val angle = (i * 360f / sparkleCount + rotation1 * 0.1f) % 360
                    val startX =
                        star.x * canvasWidth + cos(angle * PI.toFloat() / 180) * (star.size * 0.8f)
                    val startY =
                        star.y * canvasHeight + sin(angle * PI.toFloat() / 180) * (star.size * 0.8f)
                    val endX =
                        star.x * canvasWidth + cos(angle * PI.toFloat() / 180) * (star.size * 2.5f) // Reduced from 3.5f
                    val endY =
                        star.y * canvasHeight + sin(angle * PI.toFloat() / 180) * (star.size * 2.5f) // Reduced from 3.5f

                    drawLine(
                        color = starColor.copy(alpha = starAlpha * 0.1f), // Reduced from 0.2f
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 0.5f // Reduced from 1f
                    )
                }
            }
        }

        // Define orbit dimensions - reduced sizes for subtlety
        val orbit1RadiusX = canvasWidth * 0.38f * orbitPulse // Reduced from 0.42f
        val orbit1RadiusY = canvasHeight * 0.30f // Reduced from 0.34f
        val orbit2RadiusX = canvasWidth * 0.46f // Reduced from 0.52f
        val orbit2RadiusY = canvasHeight * 0.24f * orbitPulse // Reduced from 0.26f
        val orbit3RadiusX = canvasWidth * 0.54f * orbitPulse // Reduced from 0.62f
        val orbit3RadiusY = canvasHeight * 0.40f * orbitPulse // Reduced from 0.48f
        val orbit3Center = Offset(canvasWidth * 0.6f, canvasHeight * 0.7f)
        val circleRadius = canvasWidth * 0.16f * orbitPulse // Reduced from 0.2f
        val circleCenter = Offset(canvasWidth * 0.2f, canvasHeight * 0.25f)

        // Draw primary orbit
        drawOrbitalEllipse(
            center = center,
            radiusX = orbit1RadiusX,
            radiusY = orbit1RadiusY,
            rotation = rotation1,
            color = primaryWithAlpha,
            strokeWidth = 1.5f, // Reduced from 2.0f
            dashPattern = dashPattern1
        )

        // Draw secondary orbit
        drawOrbitalEllipse(
            center = center,
            radiusX = orbit2RadiusX,
            radiusY = orbit2RadiusY,
            rotation = rotation2,
            color = secondaryWithAlpha,
            strokeWidth = 1.8f, // Reduced from 3.0f
            dashPattern = dashPattern2
        )

        // Draw tertiary orbit
        drawOrbitalEllipse(
            center = orbit3Center,
            radiusX = orbit3RadiusX,
            radiusY = orbit3RadiusY,
            rotation = rotation1 * 0.7f,
            color = tertiaryWithAlpha,
            strokeWidth = 1.0f, // Reduced from 1.5f
            dashPattern = dashPattern3
        )

        // Draw circular orbit
        drawCircle(
            color = circleColor,
            radius = circleRadius,
            center = circleCenter,
            style =
            Stroke(
                width = 1.5f, // Reduced from 2.0f
                pathEffect = PathEffect.dashPathEffect(dashPattern2, 0f)
            )
        )

        val pointsCount = 4 // Reduced from 5
        for (i in 0 until pointsCount) {
            val angle = (i * 2 * PI / pointsCount + rotation1 * PI / 180).toFloat()
            val x = center.x + cos(angle) * orbit1RadiusX
            val y = center.y + sin(angle) * orbit1RadiusY
            drawCircle(
                color = pointColor,
                radius = 2.5f, // Reduced from 3.5f
                center = Offset(x, y)
            )
        }

        // Draw smaller points on the second orbit (reduced size and count)
        val smallPointsCount = 5 // Reduced from 7
        for (i in 0 until smallPointsCount) {
            val angle = (i * 2 * PI / smallPointsCount + rotation2 * PI / 180).toFloat()
            val x = center.x + cos(angle) * orbit2RadiusX
            val y = center.y + sin(angle) * orbit2RadiusY
            drawCircle(
                color = secondaryWithAlpha,
                radius = 2.0f, // Reduced from 2.5f
                center = Offset(x, y)
            )
        }

        // Draw subtle wave effect (reduced amplitude)
        val wavePath =
            Path().apply {
                moveTo(0f, canvasHeight * 0.5f)
                for (i in 0..canvasWidth.toInt() step 120) { // Increased step from 100 for fewer waves
                    val x = i.toFloat()
                    val y =
                        canvasHeight * 0.5f + sin((x + rotation1) * PI / 180 * 2) * 25 // Reduced amplitude from 40
                    lineTo(x, y.toFloat())
                }
            }

        drawPath(
            path = wavePath,
            color = waveFillColor,
            style =
            Stroke(
                width = 1.5f, // Reduced from 2f
                cap = StrokeCap.Round
            )
        )
    }
}

private data class Star(
    val x: Float,
    val y: Float,
    val size: Float,
    val alphaGroup: Int,
    val pointCount: Int,
    val rotation: Float
)

private data class GalaxyCluster(
    val centerX: Float,
    val centerY: Float,
    val radius: Float,
    val rotation: Float,
    val starCount: Int
)

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStar(
    center: Offset,
    outerRadius: Float,
    innerRadius: Float,
    points: Int,
    color: Color,
    rotation: Float
) {
    val path = Path()
    val angleStep = 360f / (points * 2)

    for (i in 0 until points * 2) {
        val radius = if (i % 2 == 0) outerRadius else innerRadius
        val angle = (i * angleStep + rotation) * PI.toFloat() / 180
        val x = center.x + cos(angle) * radius
        val y = center.y + sin(angle) * radius

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }

    path.close()
    drawPath(path, color)
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawOrbitalEllipse(
    center: Offset,
    radiusX: Float,
    radiusY: Float,
    rotation: Float,
    color: Color,
    strokeWidth: Float = 2.0f,
    dashPattern: FloatArray
) {
    rotate(degrees = rotation, pivot = center) {
        drawOval(
            color = color,
            topLeft = Offset(center.x - radiusX, center.y - radiusY),
            size = Size(radiusX * 2, radiusY * 2),
            style =
            Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(dashPattern, phase = rotation % 30)
            )
        )
    }
}
