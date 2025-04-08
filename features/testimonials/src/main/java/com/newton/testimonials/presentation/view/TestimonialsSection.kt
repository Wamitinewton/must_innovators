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
package com.newton.testimonials.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.network.domain.models.testimonials.*
import com.newton.testimonials.presentation.state.*
import kotlinx.coroutines.*

@Composable
fun TestimonialsSection(
    uiState: GetTestimonialsUiState,
    onRetryClick: () -> Unit,
    onTestimonialClick: (TestimonialsData) -> Unit = {}
) {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        when (uiState) {
            is GetTestimonialsUiState.Loading -> {
                LoadingIndicator(
                    text = "Loading Testimonials"
                )
            }

            is GetTestimonialsUiState.Success -> {
                val testimonials = uiState.testimonials
                if (testimonials.isNotEmpty()) {
                    PagedTestimonials(
                        testimonials = testimonials,
                        onTestimonialClick = onTestimonialClick
                    )
                } else {
                    EmptyStateCard(
                        icon = Icons.Default.Refresh,
                        title = "OOOOPS",
                        message = "No testimonials found. Check back in later",
                        buttonText = "Retry",
                        onActionClick = onRetryClick
                    )
                }
            }

            is GetTestimonialsUiState.Error -> {
                val errorMessage = uiState.message
                ErrorScreen(
                    titleText = "Failed to load TESTIMONIALS",
                    message = errorMessage,
                    onRetry = { onRetryClick() }
                )
            }

            GetTestimonialsUiState.Initial -> {
            }
        }
    }
}

@Composable
fun PagedTestimonials(
    testimonials: List<TestimonialsData>,
    onTestimonialClick: (TestimonialsData) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { testimonials.size })

    LaunchedEffect(testimonials) {
        while (true) {
            delay(5000)
            if (testimonials.size > 1) {
                val nextPage = (pagerState.currentPage + 1) % testimonials.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) { page ->
            TestimonialCard(
                testimonialsData = testimonials[page],
                onClick = { onTestimonialClick(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (testimonials.size > 1) {
            PagerIndicator(
                pagerState = pagerState,
                pageCount = testimonials.size,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}



@Composable
fun TestimonialAvatar(imageUrl: String?) {
    Box(
        modifier =
        Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
    ) {
        NetworkImage(
            imageUrl = imageUrl
        )
    }
}

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageCount: Int
) {
    val MAX_TOTAL_INDICATORS = 4

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(minOf(pageCount, MAX_TOTAL_INDICATORS)) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                }

            val alpha =
                when {
                    iteration < 4 -> 1f
                    iteration < MAX_TOTAL_INDICATORS -> {
                        maxOf(0f, 1f - (iteration - 3) * 0.3f)
                    }

                    else -> 0f
                }

            Box(
                modifier =
                Modifier
                    .padding(4.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = alpha))
            )
        }
    }
}
