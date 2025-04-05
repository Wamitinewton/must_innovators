package com.newton.admin.presentation.home.view.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.*
import com.newton.core.domain.models.admin.*
import com.newton.core.domain.models.adminModels.*

@Composable
fun InteractiveLineGraph(
    data: List<Pair<String, Int>>,
    onTooltipChanged: (ToolTipData?) -> Unit
) {
    var hoveredPoint by remember { mutableStateOf<Int?>(null) }

    Canvas(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        val xStep = size.width / (data.size - 1)
                        val pointIndex = (offset.x / xStep).toInt()

                        if (pointIndex in data.indices) {
                            val (date, count) = data[pointIndex]
                            onTooltipChanged(
                                ToolTipData(
                                    title = date,
                                    value = "$count active users",
                                    position = offset
                                )
                            )
                            hoveredPoint = pointIndex
                        }
                    },
                    onTap = { hoveredPoint = null }
                )
            }
    ) {
        val maxUsers = data.maxOfOrNull { it.second }?.toFloat() ?: 0f
        val xStep = size.width / (data.size - 1)

        // Animated path
//        val animatedProgress by animateFloatAsState(
//            targetValue = 1f,
//            animationSpec = tween(durationMillis = 1500)
//        )

        val path = Path()
        data.forEachIndexed { index, (_, count) ->
            val x = index * xStep
            val y = size.height - (count / maxUsers) * size.height

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                val prevX = (index - 1) * xStep
                val prevY = size.height - (data[index - 1].second / maxUsers) * size.height

                // Bezier curve for smooth line
                val controlX1 = prevX + (x - prevX) * 0.5f
                path.cubicTo(
                    controlX1,
                    prevY,
                    controlX1,
                    y,
                    x,
                    y
                )
            }

            // Draw points with hover effect
            if (index == hoveredPoint) {
                drawCircle(
                    color = DashboardColors.accent,
                    radius = 8f,
                    center = Offset(x, y)
                )
            } else {
                drawCircle(
                    color = DashboardColors.secondary,
                    radius = 4f,
                    center = Offset(x, y)
                )
            }
        }

        // Draw animated path
        drawPath(
            path = path,
            color = DashboardColors.secondary,
            style =
            Stroke(
                width = 3f,
                pathEffect = PathEffect.cornerPathEffect(10f)
            ),
            alpha = 1f
        )
    }
}
