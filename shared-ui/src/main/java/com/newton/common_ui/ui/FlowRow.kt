package com.newton.common_ui.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
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

        layout(rowWidth.coerceAtMost(constraints.maxWidth), height.coerceAtMost(constraints.maxHeight)) {
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