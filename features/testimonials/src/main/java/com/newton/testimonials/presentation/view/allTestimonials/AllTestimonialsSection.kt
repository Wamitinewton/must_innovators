package com.newton.testimonials.presentation.view.allTestimonials

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.network.domain.models.testimonials.*

@Composable
fun AllTestimonialsSection(testimonials: List<TestimonialsData>) {
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(300)) + scaleIn(
                initialScale = 0.95f,
                animationSpec = tween(300)
            )
        ) {
            RatingSummary(testimonials)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = testimonials,
                key = { it.id }
            ) { testimonial ->
                AllTestimonialCard(testimonial)
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

}