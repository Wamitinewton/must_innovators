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
