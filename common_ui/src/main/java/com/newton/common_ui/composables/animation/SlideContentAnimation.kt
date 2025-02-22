package com.newton.common_ui.composables.animation

import android.view.FrameMetrics.ANIMATION_DURATION
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.ui.unit.IntOffset

fun AnimatedContentTransitionScope<Int>.slidingContentAnimation(): ContentTransform {
    val animationSpec: TweenSpec<IntOffset> = tween(ANIMATION_DURATION)
    val direction = getTransitionDirection(initialState, targetState)
    return slideIntoContainer(
        towards = direction,
        animationSpec = animationSpec,
    ) togetherWith slideOutOfContainer(
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