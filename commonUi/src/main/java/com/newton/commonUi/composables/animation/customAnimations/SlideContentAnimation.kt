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
