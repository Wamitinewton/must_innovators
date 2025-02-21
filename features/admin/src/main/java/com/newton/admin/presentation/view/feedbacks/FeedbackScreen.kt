package com.newton.admin.presentation.view.feedbacks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FeedbackScreen(modifier: Modifier = Modifier) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(it)){
            Box(modifier = Modifier.fillMaxSize().align(Alignment.Center)){
                Text("Feedback Screen")
            }
        }
    }
}