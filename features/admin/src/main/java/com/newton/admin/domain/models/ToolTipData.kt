package com.newton.admin.domain.models

import androidx.compose.ui.geometry.Offset

data class TooltipData(
    val title: String,
    val value: String,
    val position: Offset
)