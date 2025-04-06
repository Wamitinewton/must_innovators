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
package com.newton.commonUi.ui

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables =
            measurables.map { measurable ->
                measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
            }

        var yPosition = 0
        var xPosition = 0
        var rowHeight = 0
        var rowWidth = 0

        val width = constraints.maxWidth

        // Calculate height
        placeables.forEach { placeable ->
            if (xPosition + placeable.width > width) {
                xPosition = 0
                yPosition += rowHeight
                rowHeight = 0
            }

            xPosition += placeable.width
            rowWidth = maxOf(rowWidth, xPosition)
            rowHeight = maxOf(rowHeight, placeable.height)
        }

        // Total height (adding the last row height)
        val height = yPosition + rowHeight

        // Reset positions for actual placement
        yPosition = 0
        xPosition = 0
        rowHeight = 0

        layout(
            rowWidth.coerceAtMost(constraints.maxWidth),
            height.coerceAtMost(constraints.maxHeight)
        ) {
            placeables.forEach { placeable ->
                if (xPosition + placeable.width > width) {
                    xPosition = 0
                    yPosition += rowHeight
                    rowHeight = 0
                }

                placeable.placeRelative(xPosition, yPosition)
                xPosition += placeable.width
                rowHeight = maxOf(rowHeight, placeable.height)
            }
        }
    }
}
