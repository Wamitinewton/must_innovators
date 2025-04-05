package com.newton.commonUi.composables.animation.customAnimations

import android.view.FrameMetrics.ANIMATION_DURATION
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.unit.*

fun AnimatedContentTransitionScope<Int>.slidingContentAnimation(): ContentTransform {
    val animationSpec: TweenSpec<IntOffset> = tween(ANIMATION_DURATION)
    val direction = getTransitionDirection(initialState, targetState)
    return slideIntoContainer(
        towards = direction,
        animationSpec = animationSpec
    ) togetherWith
        slideOutOfContainer(
            towards = direction,
            animationSpec = animationSpec
        )
}

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}
