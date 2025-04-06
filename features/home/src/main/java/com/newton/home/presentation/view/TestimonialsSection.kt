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
package com.newton.home.presentation.view

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
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import com.newton.commonUi.ui.*
import com.newton.core.utils.*
import com.newton.home.presentation.states.*
import com.newton.network.domain.models.testimonials.*
import kotlinx.coroutines.*
import kotlin.math.*

@Composable
fun TestimonialsSection(
    uiState: TestimonialsUiState,
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
            is TestimonialsUiState.Loading -> {
                LoadingIndicator(
                    text = "Loading Testimonials"
                )
            }

            is TestimonialsUiState.Success -> {
                val testimonials = uiState.testimonials
                if (testimonials.isNotEmpty()) {
                    AutoScrollingTestimonials(
                        testimonials = testimonials,
                        onTestimonialClick = onTestimonialClick
                    )
                } else {
                    EmptyStateCard(
                        icon = Icons.Default.Refresh,
                        title = "Ooops",
                        message = "No testimonials found. Check back in later",
                        buttonText = "Retry",
                        onActionClick = onRetryClick
                    )
                }
            }

            is TestimonialsUiState.Error -> {
                val errorMessage = uiState.message
                ErrorScreen(
                    titleText = "Failed to load TESTIMONIALS",
                    message = errorMessage,
                    onRetry = { onRetryClick() }
                )
            }

            TestimonialsUiState.Initial -> {
            }
        }
    }
}

@Composable
fun AutoScrollingTestimonials(
    testimonials: List<TestimonialsData>,
    onTestimonialClick: (TestimonialsData) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { testimonials.size })
    var showDetailSheet by remember { mutableStateOf<TestimonialsData?>(null) }

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
fun TestimonialCard(
    testimonialsData: TestimonialsData,
    onClick: (TestimonialsData) -> Unit
) {
    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick(testimonialsData) },
        shape = RoundedCornerShape(16.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier =
            Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.FormatQuote,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                modifier = Modifier.size(36.dp)
            )

            Text(
                text = testimonialsData.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier =
                Modifier
                    .padding(vertical = 12.dp)
                    .weight(1f),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TestimonialAvatar(imageUrl = "")
                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = testimonialsData.user_name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = formatDateTime(testimonialsData.created_at),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row {
                    repeat(min(3, testimonialsData.rating)) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    if (testimonialsData.rating > 3) {
                        Text(
                            text = "+${testimonialsData.rating - 3}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
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
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Author Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = "N",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier =
                Modifier
                    .align(Alignment.Center)
            )
        }
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
