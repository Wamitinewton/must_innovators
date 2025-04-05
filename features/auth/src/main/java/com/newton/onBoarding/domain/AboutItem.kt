package com.newton.onBoarding.domain

import androidx.compose.runtime.*

data class AboutItem(
    val title: String,
    val description: String,
    val icon: @Composable () -> Unit
)
