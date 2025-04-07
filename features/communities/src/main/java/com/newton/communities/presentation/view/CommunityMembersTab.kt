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
package com.newton.communities.presentation.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import com.newton.core.utils.*
import com.newton.network.domain.models.aboutUs.*
import kotlin.random.*

@Composable
fun MembersTab(
    members: List<Member>,
    onMemberClick: (Member) -> Unit
) {
    LazyColumn(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(members) { index, member ->
            val staggerDelay = index * 50

            AnimatedVisibility(
                visible = true,
                enter =
                fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = staggerDelay)) +
                    slideInHorizontally(
                        animationSpec = tween(durationMillis = 300, delayMillis = staggerDelay),
                        initialOffsetX = { it / 2 }
                    )
            ) {
                MemberCard(member = member, onClick = { onMemberClick(member) })
            }
        }
    }
}

@Composable
fun MemberCard(
    member: Member,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = member.name.first().toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = member.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Joined ${formatDateTime(member.joinedAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "View details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun MemberDetailsDialog(
    member: Member,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties =
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        val animatedScale = remember { Animatable(0.8f) }

        LaunchedEffect(Unit) {
            animatedScale.animateTo(
                targetValue = 1f,
                animationSpec =
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }

        ElevatedCard(
            modifier =
            Modifier
                .fillMaxWidth()
                .scale(animatedScale.value),
            shape = RoundedCornerShape(16.dp),
            colors =
            CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = member.name.first().toString(),
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = member.name,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Member details
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetailItem(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = member.email
                    )

                    DetailItem(
                        icon = Icons.Default.CalendarToday,
                        label = "Joined",
                        value = formatDateTime(member.joinedAt)
                    )

                    DetailItem(
                        icon = Icons.Default.Star,
                        label = "Contributions",
                        value = "${Random.nextInt(5, 50)} projects"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Close")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { /* Contact functionality */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Contact")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
