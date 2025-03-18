package com.newton.common_ui.composables.animation.custom_animations

object SearchBarTransformation {

    fun Float.normalizeScrollOffset(
        maxOffset: Float,
        scrollThreshold: Float = 0.15f * maxOffset
    ): Float {
        return when {
            this <= 0f -> 0f
            this >= scrollThreshold -> 1f
            else -> (this / scrollThreshold).coerceIn(0f, 1f)
        }
    }


    // Calculate scale factor based on animation progress
    fun calculateScale(progress: Float): Float {
        return 1f - (0.6f * progress)
    }

    // Calculate alpha values for search bar and icon
    fun calculateSearchBarAlpha(progress: Float): Float {
        return 1f - progress
    }

    fun calculateIconAlpha(progress: Float): Float {
        return progress
    }

    // Calculate position offset for the search bar
    fun calculatePositionOffset(
        progress: Float,
        maxWidth: Float,
        maxHeight: Float
    ): Pair<Float, Float> {
        val xOffset = progress * maxWidth * 0.35f
        val yOffset = -progress * maxHeight * 0.5f
        return xOffset to yOffset
    }
}