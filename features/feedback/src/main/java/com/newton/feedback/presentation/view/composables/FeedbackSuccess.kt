package com.newton.feedback.presentation.view.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*

@Composable
fun SuccessAnimation() {
    var animationState by remember { mutableStateOf<LottieAnimationState>(LottieAnimationState.Playing) }
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EnhancedLottieAnimation(
                lottieFile = com.newton.commonUi.R.raw.innovators_success,
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
