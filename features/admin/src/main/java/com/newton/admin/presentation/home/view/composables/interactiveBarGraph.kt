package com.newton.admin.presentation.home.view.composables

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.home.view.*
import com.newton.core.domain.models.admin.*
import com.newton.core.domain.models.adminModels.*

@Composable
fun InteractiveBarGraph(
    data: List<InteractionData>,
    isWeeklyView: Boolean,
    onTooltipChanged: (ToolTipData?) -> Unit
) {
    var hoveredBar by remember { mutableStateOf<Int?>(null) }

    val groupedData =
        if (isWeeklyView) {
            data.chunked(7).map { weekData ->
                InteractionData(
                    day = "${weekData.first().day} - ${weekData.last().day}",
                    intensity = weekData.sumOf { it.intensity }
                )
            }
        } else {
            data
        }

    Canvas(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        val barWidth = size.width / groupedData.size - 10f
                        val barIndex = (offset.x / (barWidth + 10f)).toInt()

                        if (barIndex in groupedData.indices) {
                            val interaction = groupedData[barIndex]
                            onTooltipChanged(
                                ToolTipData(
                                    title = interaction.day,
                                    value = "${interaction.intensity} interactions",
                                    position = offset
                                )
                            )
                            hoveredBar = barIndex
                        }
                    },
                    onTap = {
                        hoveredBar = null
                        onTooltipChanged(null)
                    }
                )
            }
    ) {
        val maxCount = groupedData.maxOfOrNull { it.intensity }?.toFloat() ?: 0f
        val barWidth = size.width / groupedData.size - 10f

        groupedData.forEachIndexed { index, data ->
            val barHeight = (data.intensity / maxCount) * size.height
            val x = index * (barWidth + 10f)

            // Enhanced animation with spring effect
//            val animatedHeight by animateFloatAsState(
//                targetValue = barHeight,
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                    stiffness = Spring.StiffnessLow
//                )
//            )

            // Bar color with hover effect
            val barColor =
                if (index == hoveredBar) {
                    DashboardColors.accent
                } else {
                    DashboardColors.primary
                }

            // Draw bar with rounded corners
            drawRoundRect(
                color = barColor,
                topLeft = Offset(x, size.height - barHeight),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(8f, 8f)
            )

            // Draw value label on top of bar
            if (index == hoveredBar) {
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        data.intensity.toString(),
                        x + barWidth / 2,
                        size.height - barHeight - 10,
                        Paint().apply {
                            color = Color.BLACK
                            textSize = 24f
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}
