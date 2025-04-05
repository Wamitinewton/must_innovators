package com.newton.blogs.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*

@Composable
fun BlogsScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()) { values ->
        Box(
            modifier =
            Modifier.padding(
                top = values.calculateTopPadding(),
                bottom = values.calculateBottomPadding()
            )
        ) {
            Text("Blogs Screen")
        }
    }
}
