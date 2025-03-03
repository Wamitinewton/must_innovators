package com.newton.admin.presentation.home.views.composables

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.newton.admin.domain.models.DashboardColors
import com.newton.admin.domain.models.TooltipData
import com.newton.admin.presentation.home.views.EventData
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun EventsPieChart(
    events: List<EventData>,
    onTooltipChanged: (TooltipData?) -> Unit,
) {
    var selectedSegment by remember { mutableStateOf<Int?>(null) }
    var rotationAngle by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        // Initial animation
        rotationAngle = 360f
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
//                    onPress = { offset ->
//                        val center = Offset(size.width / 2, size.height / 2)
//                        val radius = size.minDimension / 2
//
//                        if (offset.distanceTo(center) <= radius) {
//                            val angle = (atan2(
//                                offset.y - center.y,
//                                offset.x - center.x
//                            ) * 180 / PI).toFloat()
//
//                            var currentAngle = 0f
//                            events.forEachIndexed { index, event ->
//                                val sweepAngle = (event.eventCount.toFloat() /
//                                        events.sumOf { it.eventCount }) * 360f
//
//                                if (angle >= currentAngle &&
//                                    angle <= currentAngle + sweepAngle) {
//                                    selectedSegment = index
//                                    onTooltipChanged(
//                                        TooltipData(
//                                            title = event.communityName,
//                                            value = "${event.eventCount} events",
//                                            position = offset
//                                        )
//                                    )
//                                    return@detectTapGestures
//                                }
//                                currentAngle += sweepAngle
//                            }
//                        }
//                    },
                    onTap = {
                        selectedSegment = null
                        onTooltipChanged(null)
                    }
                )
            }
    ) {
        val center = Offset(size.width / 2, size.height / 2)
//        val animatedRotation by animateFloatAsState(
//            targetValue = rotationAngle,
//            animationSpec = spring(
//                dampingRatio = Spring.DampingRatioMediumBouncy,
//                stiffness = Spring.StiffnessLow
//            )
//        )

        val total = events.sumOf { it.events }.toFloat()
        var startAngle = rotationAngle

        events.forEachIndexed { index, event ->
            val sweepAngle = (event.events / total) * 360f
            val isSelected = selectedSegment == index

            // Animated radius for selected segment
//            val radius by animateFloatAsState(
//                targetValue = if (isSelected) {
//                    size.minDimension / 2 * 1.1f
//                } else {
//                    size.minDimension / 2
//                },
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                    stiffness = Spring.StiffnessLow
//                )
//            )

            // Draw segment
            drawArc(
                color = DashboardColors.chartColors[index % DashboardColors.chartColors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(size.minDimension, size.minDimension),
//                topLeft = Offset(
//                    center.x - (size.minDimension*2),
//                    center.y - (size.minDimension*2)
//                )
            )

            // Draw percentage label
            if (sweepAngle > 30) {  // Only draw label if segment is large enough
                val labelRadius = size.minDimension * 0.4f
                val labelAngle = startAngle + sweepAngle / 2
                val labelX = center.x + cos(labelAngle * PI / 180f).toFloat() * labelRadius
                val labelY = center.y + sin(labelAngle * PI / 180f).toFloat() * labelRadius

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "${(event.events / total * 100).toInt()}%",
                        labelX,
                        labelY,
                        Paint().apply {
                            color = android.graphics.Color.WHITE
                            textSize = 30f
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
            startAngle += sweepAngle
        }
    }
}