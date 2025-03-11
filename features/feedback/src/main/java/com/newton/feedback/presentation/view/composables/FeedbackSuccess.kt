package com.newton.feedback.presentation.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.EnhancedLottieAnimation
import com.newton.common_ui.ui.LottieAnimationState

@Composable
fun SuccessAnimation() {

    var animationState by remember { mutableStateOf<LottieAnimationState>(LottieAnimationState.Playing) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EnhancedLottieAnimation(
                lottieFile = com.newton.common_ui.R.raw.innovators_success,
                animateEntry = true,
                iterations = 1,
                modifier = Modifier.size(200.dp),
                onAnimationFinish = {
                    animationState = LottieAnimationState.Completed
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Thank You",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your feedback has been submitted",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}