package com.newton.on_boarding.domain

import androidx.compose.runtime.Composable

data class AboutItem(
    val title: String,
    val description: String,
    val icon: @Composable () -> Unit
)
