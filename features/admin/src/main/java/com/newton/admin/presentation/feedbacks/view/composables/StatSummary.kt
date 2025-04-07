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
package com.newton.admin.presentation.feedbacks.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.newton.core.enums.*
import com.newton.network.domain.models.adminModels.*

@Composable
fun StatsSummary(feedbacks: List<FeedbackData>) {
    val pendingCount =
        remember(feedbacks) { feedbacks.count { it.status == FeedbackStatus.PENDING } }
    val inProgressCount =
        remember(feedbacks) { feedbacks.count { it.status == FeedbackStatus.IN_PROGRESS } }
    val completedCount =
        remember(feedbacks) { feedbacks.count { it.status == FeedbackStatus.RESOLVED } }
    val criticalCount =
        remember(feedbacks) { feedbacks.count { it.priority == FeedbackPriority.CRITICAL } }

    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                value = pendingCount,
                label = "Pending",
                icon = Icons.Outlined.AccessTime,
                color = MaterialTheme.colorScheme.tertiary
            )

            VerticalDivider(
                modifier =
                Modifier
                    .width(1.dp)
                    .height(40.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            StatItem(
                value = inProgressCount,
                label = "In Progress",
                icon = Icons.Outlined.Edit,
                color = MaterialTheme.colorScheme.secondary
            )

            VerticalDivider(
                modifier =
                Modifier
                    .height(40.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            StatItem(
                value = completedCount,
                label = "Completed",
                icon = Icons.Outlined.CheckCircle,
                color = MaterialTheme.colorScheme.primary
            )

            VerticalDivider(
                modifier =
                Modifier
                    .height(40.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            StatItem(
                value = criticalCount,
                label = "Critical",
                icon = Icons.Outlined.Warning,
                color = Color.Red
            )
        }
    }
}
