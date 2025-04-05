package com.newton.admin.presentation.events.view.management.composables.feedback

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.core.domain.models.adminModels.*
import kotlinx.coroutines.*
import java.time.format.*

@Composable
fun FeedbackCard(
    feedback: EventsFeedback,
    isScrolling: Boolean
) {
    val animatedElevation = remember { Animatable(2f) }
    val animatedRotation = remember { Animatable(0f) }
    val density = LocalDensity.current

    // Cool animations when scrolling
    LaunchedEffect(isScrolling) {
        if (isScrolling) {
            // Elevation animation
            launch {
                animatedElevation.animateTo(
                    targetValue = 8f,
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                )
                animatedElevation.animateTo(
                    targetValue = 2f,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
            }

            // Slight rotation animation
            launch {
                val direction = if (feedback.id.hashCode() % 2 == 0) 1f else -1f
                animatedRotation.animateTo(
                    targetValue = 1f * direction,
                    animationSpec = tween(200, easing = FastOutSlowInEasing)
                )
                animatedRotation.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
        }
    }

    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = with(density) { animatedElevation.value.dp },
                shape = RoundedCornerShape(16.dp)
            )
            .background(color = MaterialTheme.colorScheme.surface)
            .rotate(animatedRotation.value),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    brush =
                    Brush.verticalGradient(
                        colors =
                        listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = feedback.attendeeId,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = feedback.submittedAt.format(DateTimeFormatter.ofPattern("MMM d, yyyy")),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = feedback.attendeeName,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )

                    RatingBar(
                        rating = feedback.rating.toFloat(),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                // Animated star rating state
                val animatedRating = remember { mutableFloatStateOf(0f) }

                val animatedRatingValue by animateFloatAsState(
                    targetValue = animatedRating.floatValue,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
                LaunchedEffect(feedback.id) {
                    animatedRating.floatValue = 0f
                    delay(300)
                    animatedRating.floatValue = feedback.rating.toFloat()
                }

                Surface(
                    shape = CircleShape,
                    color =
                    when (feedback.rating) {
                        5 -> Color(0xFF4CAF50)
                        4 -> Color(0xFF8BC34A)
                        3 -> Color(0xFFFFC107)
                        2 -> Color(0xFFFF9800)
                        else -> Color(0xFFF44336)
                    }
                ) {
                    Text(
                        text = feedback.rating.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Comment with quote style
            Surface(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(2.dp),
                        modifier =
                        Modifier
                            .width(4.dp)
                            .height(IntrinsicSize.Max)
                    ) {
                        Spacer(modifier = Modifier.fillMaxHeight())
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = feedback.comment,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}
