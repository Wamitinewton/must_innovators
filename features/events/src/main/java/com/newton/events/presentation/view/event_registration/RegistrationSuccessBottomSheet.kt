package com.newton.events.presentation.view.event_registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.animation.confetti_animation.DottedShape
import com.newton.common_ui.composables.animation.confetti_animation.ConfettiCelebration
import com.newton.common_ui.composables.animation.confetti_animation.GlobalConfettiEffect
import com.newton.common_ui.ui.CustomElevatedButton
import com.newton.common_ui.ui.CustomOutlinedButton
import com.newton.common_ui.ui.LabelLargeText
import com.newton.core.domain.models.admin_models.RegistrationResponse
import com.newton.core.utils.formatDateTime
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventRegistrationSuccessScreen(
    registrationResponse: RegistrationResponse,
    eventName: String,
    eventDateTime: String,
    onNavigateToHome: () -> Unit,
    onViewMyTickets: () -> Unit
) {
    var showScreen by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }
    var showTicket by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        showScreen = true
        delay(300)
        showConfetti = true
        delay(800)
        showTicket = true
        delay(600)
        showButtons = true
    }

    AnimatedVisibility(
        visible = showScreen,
        enter = fadeIn(tween(700)) + scaleIn(
            initialScale = 0.8f,
            animationSpec = tween(700, easing = FastOutSlowInEasing)
        )
    ) {
        Scaffold(
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                AnimatedVisibility(
                    visible = showConfetti,
                    enter = fadeIn(tween(300))
                ) {
                    GlobalConfettiEffect(
                        modifier = Modifier.fillMaxSize()
                    )
                }

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
                        visible = showConfetti,
                        enter = fadeIn() + scaleIn(initialScale = 0.5f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ConfettiCelebration()
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Registration Successful!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "You're all set for the event",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    AnimatedVisibility(
                        visible = showTicket,
                        enter = fadeIn(tween(500)) +
                                slideInVertically(
                                    initialOffsetY = { it / 2 },
                                    animationSpec = tween(500, easing = EaseInOut)
                                )
                    ) {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            elevation = CardDefaults.elevatedCardElevation(
                                defaultElevation = 6.dp
                            ),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp)
                            ) {
                                Text(
                                    text = "EVENT TICKET",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = eventName,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                TicketInfoRow(
                                    icon = Icons.Default.QrCode2,
                                    label = "Ticket #",
                                    value = registrationResponse.ticketNumber
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                TicketInfoRow(
                                    icon = Icons.Default.AccessTime,
                                    label = "Date & Time",
                                    value = eventDateTime
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                TicketInfoRow(
                                    icon = Icons.Default.Person,
                                    label = "Attendee",
                                    value = registrationResponse.fullName
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                TicketInfoRow(
                                    icon = Icons.Default.School,
                                    label = "Course & Year",
                                    value = "${registrationResponse.course} (${registrationResponse.educationalLevel})"
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.outlineVariant,
                                            shape = DottedShape(step = 10.dp)
                                        )
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = formatDateTime(registrationResponse.registrationTimestamp),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    AnimatedVisibility(
                        visible = showButtons,
                        enter = fadeIn(tween(500)) +
                                slideInVertically(
                                    initialOffsetY = { it / 2 },
                                    animationSpec = tween(500)
                                )
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                           CustomElevatedButton(
                               onClick = onViewMyTickets,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .height(56.dp),
                               shape = RoundedCornerShape(16.dp),
                               content = {
                                   LabelLargeText(
                                       text = "View My Tickets"
                                   )
                               }
                           )

                            Spacer(modifier = Modifier.height(16.dp))

                            CustomOutlinedButton(
                                onClick = onNavigateToHome,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                content = {
                                    LabelLargeText(
                                        text = "Back to Home"
                                    )
                                },
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
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
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}