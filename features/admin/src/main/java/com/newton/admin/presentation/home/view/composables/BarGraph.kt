/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
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
import com.newton.network.domain.models.admin.*
import com.newton.network.domain.models.adminModels.*

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
