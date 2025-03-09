package com.newton.admin.presentation.feedbacks.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.newton.admin.domain.models.FeedBack
import com.newton.admin.domain.models.enums.FeedbackPriority
import com.newton.admin.domain.models.enums.FeedbackStatus


@Composable
fun StatsSummary(feedbacks: List<FeedBack>) {
    val pendingCount = remember(feedbacks) { feedbacks.count { it.status == FeedbackStatus.PENDING } }
    val inProgressCount = remember(feedbacks) { feedbacks.count { it.status == FeedbackStatus.IN_PROGRESS } }
    val completedCount = remember(feedbacks) { feedbacks.count { it.status == FeedbackStatus.RESOLVED } }
    val criticalCount = remember(feedbacks) { feedbacks.count { it.priority == FeedbackPriority.CRITICAL } }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
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
                modifier = Modifier
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
                modifier = Modifier
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
                modifier = Modifier
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
