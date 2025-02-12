package com.newton.common_ui.ui

import androidx.annotation.RawRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun EnhancedLottieAnimation(
    modifier: Modifier = Modifier,
    @RawRes lottieFile: Int,
    alignment: Alignment = Alignment.Center,
    enableMergePaths: Boolean = true,
    iterations: Int = 1,
    reverseAnimation: Boolean = false,
    size: Int = 200,
    animateEntry: Boolean = false,
    onAnimationFinish: () -> Unit = {}
) {
    var isVisible by remember { mutableStateOf(!animateEntry) }
    val scale = remember { Animatable(if (animateEntry) 0.8f else 1f) }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(lottieFile)
    )

    // Entry animation
    LaunchedEffect(Unit) {
        if (animateEntry) {
            isVisible = true
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    // Main animation logic
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        isPlaying = true,
        restartOnPlay = false,
        clipSpec = if (reverseAnimation) {
            LottieClipSpec.Progress(0f, 1f)
        } else {
            LottieClipSpec.Progress(0f, 1f)
        },
        speed = if (reverseAnimation) -1f else 1f
    )

    // Animation completion callback
    LaunchedEffect(progress) {
        if (progress == 1f) {
            onAnimationFinish()
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = alignment
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .size(size.dp)
                .scale(scale.value),
            enableMergePaths = remember { enableMergePaths },
            alignment = alignment,
        )
    }
}

sealed class LottieAnimationState {
    data object Playing : LottieAnimationState()
    data object Completed : LottieAnimationState()
}

@Composable
fun SuccessLottieAnimation(
    @RawRes lottieFile: Int,
    onAnimationComplete: () -> Unit = {}
) {
    var animationState by remember { mutableStateOf<LottieAnimationState>(LottieAnimationState.Playing) }

    EnhancedLottieAnimation(
        lottieFile = lottieFile,
        animateEntry = true,
        iterations = 1,
        onAnimationFinish = {
            animationState = LottieAnimationState.Completed
            onAnimationComplete()
        }
    )
}