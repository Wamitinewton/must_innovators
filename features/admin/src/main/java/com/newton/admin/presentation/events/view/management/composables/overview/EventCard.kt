package com.newton.admin.presentation.events.view.management.composables.overview

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
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.adminModels.*
import java.time.*
import java.time.format.*

@Composable
fun EventCard(
    event: EventsData,
    isScrolling: Boolean,
    onClick: () -> Unit
) {
    val isPast = event.date.toLocalDateTime().isBefore(LocalDateTime.now())
    val density = LocalDensity.current
    val animatedOffset = remember { Animatable(0f) }
    val infiniteTransition = rememberInfiniteTransition()

    LaunchedEffect(isScrolling) {
        if (isScrolling) {
            animatedOffset.animateTo(
                targetValue = 5f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            animatedOffset.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
        }
    }

    // Pulse animation for upcoming events with reminders
    val pulseScale =
        if (!isPast && event.isVirtual) {
            infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.02f,
                animationSpec =
                infiniteRepeatable(
                    animation = tween(800, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        } else {
            remember { mutableStateOf(1f) }
        }

    CustomCard(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .offset {
                IntOffset(y = with(density) { animatedOffset.value.toDp().roundToPx() }, x = 0)
            }
            .clickable {
                onClick()
            }
            .scale(pulseScale.value)
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Event date circle
            Box(
                modifier =
                Modifier
                    .size(56.dp)
                    .background(
                        color =
                        if (isPast) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = event.date.toLocalDateTime()
                            .format(DateTimeFormatter.ofPattern("dd")),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color =
                        if (isPast) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        }
                    )
                    Text(
                        text = event.date.toLocalDateTime()
                            .format(DateTimeFormatter.ofPattern("MMM")),
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                        if (isPast) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = event.location,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text =
                    "${
                    event.date.toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                    } - " +
                        event.date.toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.labelMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )

//                    Text(
//                        text = "${event.attendees.count { it.isAttending }}/${event.attendees.size}",
//                        style = MaterialTheme.typography.labelMedium,
//                        modifier = Modifier.padding(start = 4.dp)
//                    )

                    if (event.isVirtual) {
                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = "Virtual Event",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View Details",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
