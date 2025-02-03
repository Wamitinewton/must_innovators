package com.newton.auth.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = {}
    ) {
        Surface(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                CircularProgressIndicator()
            }
        }
    }
}