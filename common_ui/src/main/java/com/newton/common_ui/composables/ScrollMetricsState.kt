package com.newton.common_ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember


@Composable
fun rememberScrollMetricsState(): ScrollMetricsState {
    return remember { ScrollMetricsState() }

}

class ScrollMetricsState {
    private val _scrollValue = mutableFloatStateOf(0f)
    val scrollValue: Float get() = _scrollValue.floatValue

    private var lastScrollValue = 0f
    private var scrollVelocity = 0f
    private var lastUpdateTime = 0L

    // Track scroll offset and calculate velocity
    fun onScroll(delta: Float) {
        val now = System.currentTimeMillis()
        val timeDiff = (now - lastUpdateTime).coerceAtLeast(1)

        // Calculate instantaneous velocity (pixels per millisecond)
        scrollVelocity = (delta - lastScrollValue) / timeDiff

        // Update state
        _scrollValue.floatValue = (_scrollValue.floatValue + delta).coerceAtLeast(0f)
        lastScrollValue = delta
        lastUpdateTime = now
    }

    // Get current scroll velocity
    fun getVelocity(): Float = scrollVelocity
}