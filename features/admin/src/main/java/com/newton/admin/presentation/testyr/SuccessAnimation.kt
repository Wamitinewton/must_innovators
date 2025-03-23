package com.newton.admin.presentation.testyr

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SuccessScreen(message: String = "Upload Successful!") {
    val successGreen = Color(0xFF4CAF50)
    val backgroundColor = Color(0xFFF5F9FF)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SuccessAnimation()

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = message,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Composable
fun SuccessAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    // Rotation animation
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Scale animation for the check mark
    val checkScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 500,
            easing = { fraction -> OvershootInterpolator(2f).getInterpolation(fraction) }
        ),
        label = "checkScale"
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .graphicsLayer {
                this.rotationY = rotation
            },
        contentAlignment = Alignment.Center
    ) {
        // 3D Orbiting particles
        OrbitingParticles(rotation)

        // Center circle with check mark
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFF4CAF50))
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(60.dp)
                    .scale(checkScale)
            ) {
                val strokeWidth = 8.dp.toPx()

                // Draw check mark
                drawLine(
                    color = Color.White,
                    start = Offset(size.width * 0.25f, size.height * 0.5f),
                    end = Offset(size.width * 0.45f, size.height * 0.7f),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = Color.White,
                    start = Offset(size.width * 0.45f, size.height * 0.7f),
                    end = Offset(size.width * 0.75f, size.height * 0.3f),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Composable
fun OrbitingParticles(rotation: Float) {
    val numParticles = 5
    val colors = listOf(
        Color(0xFF039BE5), // Blue
        Color(0xFFFFAB40), // Orange
        Color(0xFF7E57C2), // Purple
        Color(0xFF43A047), // Green
        Color(0xFFE53935)  // Red
    )

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Create multiple orbiting particles
        for (i in 0 until numParticles) {
            val particleRotation = rotation + (i * (360f / numParticles))
            val radians = particleRotation * PI.toFloat() / 180f

            // Calculate 3D position
            val orbitalRadius = 80.dp
            val x = cos(radians) * orbitalRadius.value
            val y = sin(radians) * orbitalRadius.value * 0.3f // Flatten to make it look more 3D

            // Calculate scale based on y position to enhance 3D effect
            val scale = (sin(radians) + 3) / 4 * 0.6f + 0.4f

            // Z-order based on y position
            val zIndex = if (sin(radians) > 0) 1f else -1f

            // Individual particle rotation
            val particleInternalRotation = rotation * (i % 2 * 2 - 1) * 1.5f

            Box(
                modifier = Modifier
                    .offset(x = x.dp, y = y.dp)
                    .graphicsLayer {
                        this.scaleX = scale
                        this.scaleY = scale
                        this.rotationZ = particleInternalRotation
                        this.alpha = if (zIndex > 0) 1f else 0.7f
                    }
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(colors[i % colors.size])
            )
        }
    }
}