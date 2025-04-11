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
package com.newton.events.presentation.view.eventRegistration

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.*
import com.newton.events.presentation.viewmodel.*
import kotlinx.coroutines.*

@Composable
fun EventRegistrationSuccessScreen(
    onNavigateToHome: () -> Unit,
    onViewMyTickets: () -> Unit,
    sharedViewModel: RsvpSharedViewModel
) {
    val ticket by sharedViewModel.eventTicket.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    var showContent by remember { mutableStateOf(false) }
    var showTicket by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(false) }

    val successIconScale by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "success icon scale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse animation"
    )

    LaunchedEffect(key1 = Unit) {
        delay(300)
        showContent = true
        delay(600)
        showTicket = true
        delay(800)
        showButtons = true
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(500)) + expandVertically(tween(500))
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(bottom = 24.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(100.dp)
                                .scale(successIconScale * pulseScale),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                        ) {}

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(80.dp)
                                .scale(successIconScale)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(500)) + slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(500)
                    )
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Registration Successful!",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "You're all set for the event",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                AnimatedVisibility(
                    visible = showTicket,
                    enter = fadeIn(tween(500)) + expandVertically(tween(500))
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 6.dp
                        ),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "EVENT TICKET",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(4.dp))
                                    ticket?.let {
                                        Text(
                                            text = it.eventDate,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            ticket?.let {
                                TicketInfoRow(
                                    icon = Icons.Default.QrCode2,
                                    label = "Ticket #",
                                    value = it.ticketNumber
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            ticket?.let {
                                TicketInfoRow(
                                    icon = Icons.Default.Person,
                                    label = "Attendee",
                                    value = it.fullName
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            TicketInfoRow(
                                icon = Icons.Default.School,
                                label = "Course & Year",
                                value = "${ticket?.course} (${ticket?.educationalLevel})"
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QrCode2,
                                    contentDescription = "QR Code",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(80.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            ticket?.registrationTimestamp?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                AnimatedVisibility(
                    visible = showButtons,
                    enter = fadeIn(tween(500)) + slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(500)
                    ),
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    ElevatedButton(
                        onClick = onViewMyTickets,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 6.dp
                        )
                    ) {
                        Text(
                            text = "View My Tickets",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun TicketInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.padding(12.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
