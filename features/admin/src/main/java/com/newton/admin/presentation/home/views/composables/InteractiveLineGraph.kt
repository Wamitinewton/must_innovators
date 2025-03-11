package com.newton.admin.presentation.home.views.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.newton.core.domain.models.admin_models.DashboardColors
import com.newton.core.domain.models.admin.TooltipData

@Composable
fun InteractiveLineGraph(
    data: List<Pair<String, Int>>,
    onTooltipChanged: (TooltipData?) -> Unit
) {
    var hoveredPoint by remember { mutableStateOf<Int?>(null) }

    Canvas(
        modifier = Modifier
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
                                TooltipData(
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
                    controlX1, prevY,
                    controlX1, y,
                    x, y
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
            style = Stroke(
                width = 3f,
                pathEffect = PathEffect.cornerPathEffect(10f)
            ),
            alpha = 1f
        )
    }
}