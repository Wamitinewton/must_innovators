package com.newton.on_boarding

import androidx.compose.runtime.Composable

data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String,
    val icon: @Composable () -> Unit
)
