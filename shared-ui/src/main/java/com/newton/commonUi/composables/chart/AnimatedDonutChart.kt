package com.newton.commonUi.composables.chart

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*

private const val DIVIDER_LENGTH_IN_DEGREES = 0f
private const val DELAY_MILLIS = 600
private const val DURATION_MILLIS = 1000

@Composable
fun AnimatedDonutChart(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val currentState =
        remember {
            MutableTransitionState(AnimatedCircleProgress.START).apply {
                targetState = AnimatedCircleProgress.END
            }
        }
    val stroke = with(LocalDensity.current) { Stroke(48.dp.toPx()) }
    val transition = rememberTransition(currentState)
    val angleOffset by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = DELAY_MILLIS,
                durationMillis = DURATION_MILLIS,
                easing = LinearOutSlowInEasing
            )
        },
        label = "angleCircle"
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            360f
        }
    }
    val shift by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = DELAY_MILLIS,
                durationMillis = DURATION_MILLIS,
                easing = CubicBezierEasing(0f, 0.75f, 0.35f, 0.85f)
            )
        },
        label = "shiftCircle"
    ) { progress -> if (progress == AnimatedCircleProgress.START) 0f else 360f }

    Canvas(modifier = modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft =
            Offset(
                halfSize.width - innerRadius,
                halfSize.height - innerRadius
            )
        val size =
            Size(
                width = innerRadius * 2,
                height = innerRadius * 2
            )
        var startAngle = shift - 90f
        proportions.forEachIndexed { index, proportion ->
            val sweep = proportion * angleOffset
            drawArc(
                color = colors[index],
                startAngle = startAngle + DIVIDER_LENGTH_IN_DEGREES / 2,
                sweepAngle = sweep - DIVIDER_LENGTH_IN_DEGREES,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style = stroke
            )
            startAngle += sweep
        }
    }
}

private enum class AnimatedCircleProgress { START, END }
