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

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.network.domain.models.admin.*

@Composable
fun TooltipBox(
    tooltipData: ToolTipData,
    onDismiss: () -> Unit
) {
    Card(
        modifier =
        Modifier
            .offset(
                x = tooltipData.position.x.dp,
                y = tooltipData.position.y.dp
            )
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier =
            Modifier
                .padding(8.dp)
        ) {
            Text(
                text = tooltipData.title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = tooltipData.value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
