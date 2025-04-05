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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.composables.chart.*
import com.newton.commonUi.ui.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun DonutChartComponentView(
    amount: Double,
    proportions: List<Float>,
    colors: List<Long>,
    categoriesName: List<String>
) {
    val formattedColors = colors.map { Color(it) }
    ColumnWrapper(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedDonutChart(
                modifier = Modifier.size(280.dp),
                proportions = proportions,
                colors = formattedColors
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    amount.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categoriesName.forEachIndexed { index, name ->
                ChartLegend(title = name, color = formattedColors[index])
            }
        }
    }
}

@Composable
private fun CustomSegmentedButtonView() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        CustomButton(
            onClick = { },
            content = {
                Text(text = "Donut Chart")
            }
        )

        CustomButton(
            onClick = { },
            content = {
                Text(text = "Bar Chart", color = MaterialTheme.colorScheme.outline)
            }
        )
    }
}

@Composable
private fun ChartLegend(
    color: Color,
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(16.dp)) {
            drawCircle(color = color)
        }

        Text(title, style = MaterialTheme.typography.labelMedium)
    }
}
