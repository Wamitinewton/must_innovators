package com.newton.on_boarding.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil3.compose.SubcomposeAsyncImage
import com.newton.common_ui.R
import com.newton.common_ui.ui.BodyLargeText
import com.newton.common_ui.ui.DisplaySmallText
import com.newton.common_ui.ui.LabelLargeText
import com.newton.on_boarding.domain.OnboardingPage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun OnboardingScreen(
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            image = R.drawable.innovation,
            title = "Join the Innovation Community",
            description = "Connect with like-minded innovators and be part of something revolutionary",
            icon = { Icon(Icons.Default.Person, contentDescription = "Community") }
        ),
        OnboardingPage(
            image = R.drawable.innovation,
            title = "Discover New Ideas",
            description = "Access resources and explore groundbreaking projects in our vibrant ecosystem",
            icon = { Icon(Icons.Default.Check, contentDescription = "Ideas") }
        ),
        OnboardingPage(
            image = R.drawable.innovation,
            title = "Start Your Journey",
            description = "Take your first step towards innovation today with MUST Innovation",
            icon = { Icon(Icons.Rounded.Email, contentDescription = "Start") }
        )
    )

    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    var showGetStarted by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
        showGetStarted = pagerState.currentPage == pages.size - 1
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSpacing = 0.dp,
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                SubcomposeAsyncImage(
                    model = pages[page].image,
                    contentDescription = "Background image for ${pages[page].title}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                            translationX = size.width * (1f - lerp(
                                0.8f,
                                1f,
                                1f - pageOffset.coerceIn(0f, 1f)
                            ))
                        }
                        .blur(4.dp),
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.9f)
                                )
                            )
                        )
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.2f))

            AnimatedVisibility(
                visibleState = visibleState,
                enter = fadeIn(animationSpec = tween(1000)) +
                        slideInVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            initialOffsetY = { -200 }
                        )
            ) {
                Card(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        pages[pagerState.currentPage].icon()
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Page content
            AnimatedVisibility(
                visibleState = visibleState,
                enter = fadeIn(animationSpec = tween(1000, delayMillis = 300)) +
                        slideInVertically(
                            animationSpec = tween(700, delayMillis = 300),
                            initialOffsetY = { 100 }
                        )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    DisplaySmallText(
                        text = pages[pagerState.currentPage].title,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BodyLargeText(
                        text = pages[pagerState.currentPage].description,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))

            AnimatedVisibility(
                visibleState = visibleState,
                enter = fadeIn(animationSpec = tween(1000, delayMillis = 600)) +
                        slideInVertically(
                            animationSpec = tween(700, delayMillis = 600),
                            initialOffsetY = { 200 }
                        )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 32.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(pages.size) { iteration ->
                                val width by animateDpAsState(
                                    targetValue = if (pagerState.currentPage == iteration) 24.dp else 10.dp,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    ),
                                    label = "indicator width"
                                )

                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .height(10.dp)
                                        .width(width)
                                        .clip(CircleShape)
                                        .background(
                                            if (pagerState.currentPage == iteration)
                                                MaterialTheme.colorScheme.primary
                                            else
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                        )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        if (showGetStarted) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                ElevatedButton(
                                    onClick = {
                                        onSignupClick()
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(56.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.elevatedButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                    ),
                                    elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
                                ) {
                                    LabelLargeText(text = "Register")
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                FilledTonalButton(
                                    onClick = {
                                        onLoginClick()
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(56.dp),
                                    colors = ButtonDefaults.filledTonalButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    LabelLargeText(
                                        text = "Login",
                                    )
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Skip button
                                TextButton(
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(pages.size - 1)
                                        }
                                    }
                                ) {
                                    LabelLargeText(
                                        text = "Skip",
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }

                                // Next button
                                FloatingActionButton(
                                    onClick = {
                                        scope.launch {
                                            val nextPage = (pagerState.currentPage + 1) % pages.size
                                            pagerState.animateScrollToPage(
                                                nextPage,
                                                animationSpec = tween(
                                                    durationMillis = 500,
                                                    easing = FastOutSlowInEasing
                                                )
                                            )
                                        }
                                    },
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Next"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        visibleState.targetState = false
        delay(100)
        visibleState.targetState = true
    }
}