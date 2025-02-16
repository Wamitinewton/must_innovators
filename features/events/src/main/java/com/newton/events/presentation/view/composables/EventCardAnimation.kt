package com.newton.events.presentation.view.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.newton.core.domain.models.event_models.EventsData

@Composable
fun EventCardAnimation(
    event: EventsData,
    onClick: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(300)
    )

    val slideAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 100f,
        animationSpec = tween(300)
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(
        modifier = Modifier
            .alpha(alphaAnimation)
            .offset(y = slideAnimation.dp)
    ) {
        EventCard(
            event = event,
            onClick = onClick
        )
    }
}