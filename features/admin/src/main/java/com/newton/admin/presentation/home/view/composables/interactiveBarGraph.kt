package com.newton.admin.presentation.home.view.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.home.view.InteractionData
import com.newton.commonUi.ui.CustomCard
import com.newton.network.domain.models.admin.ToolTipData
import com.newton.network.domain.models.adminModels.DashboardColors


@Composable
fun InteractiveBarGraph(
    data: List<InteractionData>,
) {
    var hoveredBar by remember { mutableStateOf<Int?>(null) }
    var tooltipPosition by remember { mutableStateOf<Offset?>(null) }
    var tooltipData by remember { mutableStateOf<ToolTipData?>(null) }



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            val barWidth = size.width / data.size - 10f
                            val barIndex = (offset.x / (barWidth + 10f)).toInt()

                            if (barIndex in data.indices) {
                                val interaction = data[barIndex]
                                val tooltipInfo = ToolTipData(
                                    title = interaction.day,
                                    value = "${interaction.intensity} interactions",
                                    position = offset
                                )

                                tooltipData = tooltipInfo
                                hoveredBar = barIndex
                                val barHeight = (interaction.intensity / (data.maxOfOrNull { it.intensity }?.toFloat() ?: 1f)) * size.height
                                val x = barIndex * (barWidth + 10f) + barWidth / 2
                                tooltipPosition = Offset(x, size.height - barHeight - 8)
                            }
                        },
                        onTap = {
                            hoveredBar = null
                            tooltipPosition = null
                            tooltipData = null
                        }
                    )
                }
        ) {
            val maxCount = data.maxOfOrNull { it.intensity }?.toFloat() ?: 0f
            val barWidth = size.width / data.size - 10f

            data.forEachIndexed { index, data ->
                val barHeight = (data.intensity / maxCount) * size.height
                val x = index * (barWidth + 10f)

                val barColor = if (index == hoveredBar) {
                    DashboardColors.accent
                } else {
                    DashboardColors.primary
                }
                drawRoundRect(
                    color = barColor,
                    topLeft = Offset(x, size.height - barHeight),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(8f, 8f)
                )
            }
        }

        // Render tooltip as a composable
        tooltipPosition?.let { position ->
            tooltipData?.let { data ->
                TooltipComposable(
                    tooltipData = data,
                    modifier = Modifier.offset(
                        x = with(LocalDensity.current) { position.x.toDp() - 50.dp },  // Center tooltip
                        y = with(LocalDensity.current) { position.y.toDp() - 40.dp }   // Position above bar
                    )
                )
            }
        }
    }
}

@Composable
fun TooltipComposable(
    tooltipData: ToolTipData,
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tooltipData.title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = tooltipData.value,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}