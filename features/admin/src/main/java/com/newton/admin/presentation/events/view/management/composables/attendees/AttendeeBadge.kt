package com.newton.admin.presentation.events.view.management.composables.attendees

import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*

@Composable
fun AttendeeBadge(
    backgroundColor: Color,
    contentColor: Color,
    content: @Composable () -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(16.dp)
    ) {
        content()
    }
}
