package com.newton.admin.presentation.home.view.composables

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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.newton.core.domain.models.admin_models.DashboardColors
import com.newton.core.domain.models.admin.TooltipData
import com.newton.admin.presentation.home.view.CommunityGroup

@Composable
fun BarGraph(
    communityGroups: List<CommunityGroup>,
    onTooltipChanged: (TooltipData?) -> Unit
) {
    var hoveredBar by remember { mutableStateOf<Int?>(null) }


    Canvas(
        modifier = Modifier
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
                                TooltipData(
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
            val barColor = if (index == hoveredBar) {
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