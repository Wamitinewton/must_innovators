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
package com.newton.communities.presentation.view.communityDetails.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.core.domain.models.aboutUs.*

@Composable
fun SessionsTab(sessions: List<Session>) {
    val groupedSessions = sessions.groupBy { it.day }

    LazyColumn(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        groupedSessions.forEach { (day, daySessions) ->
            item {
                Text(
                    text = day,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            itemsIndexed(daySessions) { index, session ->
                val staggerDelay = index * 50

                AnimatedVisibility(
                    visible = true,
                    enter =
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 300,
                            delayMillis = staggerDelay
                        )
                    ) +
                        slideInVertically(
                            animationSpec = tween(durationMillis = 300, delayMillis = staggerDelay),
                            initialOffsetY = { it / 2 }
                        )
                ) {
                    SessionCard(session = session)
                }
            }
        }
    }
}

@Composable
fun SessionCard(session: Session) {
    val locationIcon =
        when (session.meetingType.lowercase()) {
            "online" -> Icons.Default.Videocam
            "hybrid" -> Icons.Default.DeveloperBoard
            else -> Icons.Default.LocationOn
        }

    val locationColor =
        when (session.meetingType.lowercase()) {
            "online" -> MaterialTheme.colorScheme.tertiary
            "hybrid" -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.primary
        }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier =
                Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(locationColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = locationIcon,
                    contentDescription = null,
                    tint = locationColor,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${session.startTime} - ${session.endTime}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = session.meetingType,
                    style = MaterialTheme.typography.bodyMedium,
                    color = locationColor
                )

                Text(
                    text = session.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
