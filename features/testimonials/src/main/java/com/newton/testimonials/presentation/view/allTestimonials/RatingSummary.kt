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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.network.domain.models.testimonials.*

@Composable
fun RatingSummary(testimonials: List<TestimonialsData>) {
    val averageRating = testimonials
        .map { it.rating }
        .average()
        .let { if (it.isNaN()) 0.0 else it }
    val ratingCounts = IntArray(5) { i ->
        testimonials.count { it.rating == i + 1 }
    }
    val maxCount = ratingCounts.maxOrNull() ?: 1
    CustomCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.35f)
                    .padding(end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$averageRating",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                RatingBar(
                    rating = averageRating.toInt(),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "${testimonials.size} reviews",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .height(80.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )

            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .padding(start = 16.dp)
            ) {
                for (i in 5 downTo 1) {
                    val count = ratingCounts[i - 1]
                    val percentage = count.toFloat() / maxCount.toFloat()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$i",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.width(16.dp)
                        )

                        LinearProgressIndicator(
                            progress = { percentage },
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = when (i) {
                                5, 4 -> MaterialTheme.colorScheme.primary
                                3 -> MaterialTheme.colorScheme.tertiary
                                else -> MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                            },
                            trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        )

                        Text(
                            text = count.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.width(24.dp),
                            textAlign = TextAlign.End
                        )
                    }

                    if (i > 1) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
