package com.newton.on_boarding.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.animation.custom_animations.OrbitalsBackground
import com.newton.common_ui.ui.BodyLargeText
import com.newton.common_ui.ui.DisplayLargeText
import com.newton.common_ui.ui.DisplaySmallText
import com.newton.common_ui.ui.LabelLargeText
import com.newton.common_ui.ui.TitleLargeText
import com.newton.on_boarding.domain.AboutItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceDim,
            MaterialTheme.colorScheme.surfaceBright
        )
    )

    val visibleState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }

    val aboutItems = listOf(
        AboutItem(
            title = "Learn Cutting-Edge Technologies",
            description = "Master the latest tech stacks and development methodologies",
            icon = { Icon(Icons.Filled.Code, contentDescription = "Code", tint = MaterialTheme.colorScheme.onPrimary) }
        ),
        AboutItem(
            title = "Collaborate with Industry Experts",
            description = "Work alongside professionals and build your network",
            icon = { Icon(Icons.Filled.Group, contentDescription = "Group", tint = MaterialTheme.colorScheme.onPrimary) }
        ),
        AboutItem(
            title = "Innovate and Transform",
            description = "Turn your ideas into reality with our resources and support",
            icon = { Icon(Icons.Filled.Lightbulb, contentDescription = "Innovate", tint = MaterialTheme.colorScheme.onPrimary) }
        )
    )

    val pagerState = rememberPagerState(pageCount = { aboutItems.size })

    LaunchedEffect(Unit) {
        visibleState.value = true

        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % aboutItems.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    val logoScale by animateFloatAsState(
        targetValue = if (visibleState.value) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logo-scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                   gradientBackground
                )
        )

        OrbitalsBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            AnimatedVisibility(
                visible = visibleState.value,
                enter = fadeIn(tween(1000)) +
                        slideInVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            initialOffsetY = { -200 }
                        )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .size(120.dp)
                            .scale(logoScale),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Devices,
                                contentDescription = "Tech Logo",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    DisplayLargeText(
                        text = "Meru Science Innovators Club",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = MaterialTheme.typography.displayMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TitleLargeText(
                        text = "Empowering the next generation of tech leaders",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            AnimatedVisibility(
                visible = visibleState.value,
                enter = fadeIn(tween(1000, delayMillis = 500)) +
                        slideInVertically(
                            initialOffsetY = { 200 },
                            animationSpec = tween(durationMillis = 700, delayMillis = 500)
                        )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DisplaySmallText(
                        text = "Why Join Us?",
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) { page ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .graphicsLayer {
                                    val pageOffset =
                                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)

                                    alpha = 1f - (0.5f * pageOffset.coerceIn(-1f, 1f).absoluteValue)
                                    scaleX = 0.8f + (0.2f * (1f - pageOffset.coerceIn(-1f, 1f).absoluteValue))
                                    scaleY = 0.8f + (0.2f * (1f - pageOffset.coerceIn(-1f, 1f).absoluteValue))
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            shape = RoundedCornerShape(24.dp),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    aboutItems[page].icon
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = aboutItems[page].title,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                BodyLargeText(
                                    text = aboutItems[page].description,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }


            Spacer(modifier = Modifier.height(30.dp))

            AnimatedVisibility(
                visible = visibleState.value,
                enter = fadeIn(tween(1000, delayMillis = 1000)) +
                        slideInVertically(
                            initialOffsetY = { 200 },
                            animationSpec = tween(durationMillis = 700, delayMillis = 1000)
                        )
            ) {
                ElevatedButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
                ) {
                    LabelLargeText(
                        text = "Get Started",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                tonalElevation = 8.dp
            ) {
                AuthBottomSheetContent(
                    onDismiss = { showBottomSheet = false },
                    onLoginClick = {
                        scope.launch {
                            bottomSheetState.hide()
                            showBottomSheet = false
                            onLoginClick()
                        }
                    },
                    onSignupClick = {
                        scope.launch {
                            bottomSheetState.hide()
                            showBottomSheet = false
                            onSignupClick()
                        }
                    }
                )
            }
        }
    }
}
    }
}


