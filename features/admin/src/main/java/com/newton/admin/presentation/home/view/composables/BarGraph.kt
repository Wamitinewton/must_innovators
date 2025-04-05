package com.newton.admin.presentation.home.view.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.home.view.*
import com.newton.core.domain.models.admin.*
import com.newton.core.domain.models.adminModels.*

@Composable
fun BarGraph(
    communityGroups: List<CommunityGroup>,
    onTooltipChanged: (ToolTipData?) -> Unit
) {
    var hoveredBar by remember { mutableStateOf<Int?>(null) }

    Canvas(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        val barWidth = size.width / communityGroups.size - 10f
                        val barIndex = (offset.x / (barWidth + 10f)).toInt()

                        if (barIndex in communityGroups.indices) {
                            val group = communityGroups[barIndex]
                            onTooltipChanged(
                                ToolTipData(
                                    title = group.name,
                                    value = "${group.name} members",
                                    position = offset
                                )
                            )
                            hoveredBar = barIndex
                        }
                    },
                    onTap = { hoveredBar = null }
                )
            }
    ) {
        val maxCount = communityGroups.maxOfOrNull { it.members }?.toFloat() ?: 0f
        val barWidth = size.width / communityGroups.size - 10f

        communityGroups.forEachIndexed { index, group ->
            val barHeight = (group.members / maxCount) * size.height
            val x = index * (barWidth + 10f)

            // Enhanced animation with spring
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
                    DashboardColors.chartColors[index % DashboardColors.chartColors.size]
                        .copy(alpha = 0.8f)
                } else {
                    DashboardColors.chartColors[index % DashboardColors.chartColors.size]
                }

            drawRect(
                color = barColor,
                topLeft = Offset(x, size.height - barHeight),
                size = Size(barWidth, barHeight)
            )
        }
    }
}
