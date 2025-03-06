package com.newton.common_ui.composables.animation.confetti_animation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

 data class ConfettiPiece(
    val color: Color,
    val initialPosition: Offset,
    val size: Float,
    val angle: Float,
    val speedMultiplier: Float,
    val rotationSpeed: Float = 5f,
    val shape: Int = 0
)