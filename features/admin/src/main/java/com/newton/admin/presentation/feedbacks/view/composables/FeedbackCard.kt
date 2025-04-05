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

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.enums.*
import kotlinx.coroutines.*
import java.text.*
import java.util.*

@Composable
fun FeedbackCard(
    feedback: FeedbackData,
    onCardClick: () -> Unit,
    onActionToggle: (Int, AdminAction) -> Unit,
    modifier: Modifier = Modifier,
    index: Int
) {
    val cardAnimationDelay = 50L * (index % 10)
    var cardVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(cardAnimationDelay)
        cardVisible = true
    }

    val cardScale by animateFloatAsState(
        targetValue = if (cardVisible) 1f else 0.8f,
        animationSpec =
        tween(
            durationMillis = 300,
            easing = EaseOutBack
        )
    )

    val cardAlpha by animateFloatAsState(
        targetValue = if (cardVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    val priorityColor =
        when (feedback.priority) {
            FeedbackPriority.LOW -> MaterialTheme.colorScheme.tertiary
            FeedbackPriority.MEDIUM -> MaterialTheme.colorScheme.secondary
            FeedbackPriority.HIGH -> Color(0xFFF57F17) // Amber
            FeedbackPriority.CRITICAL -> Color(0xFFD50000) // Red
        }

    val categoryIcon =
        when (feedback.category) {
            FeedbackCategory.BUG_REPORT -> Icons.Outlined.BugReport
            FeedbackCategory.FEATURE_REQUEST -> Icons.Outlined.Lightbulb
            FeedbackCategory.GENERAL_INQUIRY -> Icons.AutoMirrored.Outlined.HelpOutline
            FeedbackCategory.ACCOUNT_ISSUE -> Icons.Outlined.AccountCircle
//        FeedbackCategory.PAYMENT_PROBLEM -> Icons.Outlined.CreditCard
            FeedbackCategory.PERFORMANCE_ISSUE -> Icons.Outlined.Speed
        }

    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    val formattedDate =
        remember(feedback.submittedAt) {
            feedback.submittedAt.toFormatedDate()
        }

    Card(
        onClick = onCardClick,
        modifier =
        modifier
            .fillMaxWidth()
            .scale(cardScale)
            .alpha(cardAlpha)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = priorityColor.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border =
        BorderStroke(
            width = 1.dp,
            color =
            when (feedback.status) {
                FeedbackStatus.PENDING -> MaterialTheme.colorScheme.surfaceVariant
                FeedbackStatus.IN_PROGRESS -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                FeedbackStatus.RESOLVED -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            }
        )
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Priority indicator
                Box(
                    modifier =
                    Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(priorityColor)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Category chip
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = categoryIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(14.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text =
                            feedback.category.name.replace("_", " ").lowercase()
                                .split(" ")
                                .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Grammar issue indicator
                if (feedback.hasGrammarIssues) {
                    Icon(
                        imageVector = Icons.Outlined.Spellcheck,
                        contentDescription = "Grammar issues detected",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Date
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // User info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User avatar
                AsyncImage(
                    model = feedback.userProfilePic,
                    contentDescription = "Profile picture of ${feedback.userName}",
                    modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = feedback.userName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = feedback.userEmail,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Status indicator
                val statusColor =
                    when (feedback.status) {
                        FeedbackStatus.PENDING -> MaterialTheme.colorScheme.tertiary
                        FeedbackStatus.IN_PROGRESS -> MaterialTheme.colorScheme.secondary
                        FeedbackStatus.RESOLVED -> MaterialTheme.colorScheme.primary
                    }

                val statusText =
                    when (feedback.status) {
                        FeedbackStatus.PENDING -> "Pending"
                        FeedbackStatus.IN_PROGRESS -> "In Progress"
                        FeedbackStatus.RESOLVED -> "Completed"
                    }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = statusColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Feedback content
            Surface(
                shape = RoundedCornerShape(8.dp),
                color =
                if (feedback.hasGrammarIssues) {
                    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                }
            ) {
                Text(
                    text = feedback.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FeedbackActionButton(
                    text = "Write",
                    icon = Icons.Default.Edit,
                    selected = feedback.status == FeedbackStatus.IN_PROGRESS,
                    onClick = { onActionToggle(feedback.id, AdminAction.WRITE) },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.width(8.dp))

                FeedbackActionButton(
                    text = "Reply",
                    icon = Icons.AutoMirrored.Filled.Reply,
                    selected = feedback.status == FeedbackStatus.RESOLVED,
                    onClick = { onActionToggle(feedback.id, AdminAction.REPLY) },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(8.dp))

                FeedbackActionButton(
                    text = "Grammar",
                    icon = Icons.Default.Spellcheck,
                    selected = feedback.hasGrammarIssues,
                    onClick = { onActionToggle(feedback.id, AdminAction.GRAMMAR_CHECK) },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (feedback.assignedTo != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Assigned to: ${feedback.assignedTo}",
                        style = MaterialTheme.typography.labelSmall,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
