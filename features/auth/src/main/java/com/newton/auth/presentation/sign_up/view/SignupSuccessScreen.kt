package com.newton.auth.presentation.sign_up.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.EnhancedLottieAnimation
import com.newton.common_ui.ui.LottieAnimationState
import kotlinx.coroutines.delay

@Composable
fun SignupSuccessScreen(
    modifier: Modifier = Modifier,
    onContinueClick: () -> Unit
) {
    var animationState by remember { mutableStateOf<LottieAnimationState>(LottieAnimationState.Playing) }
    var showContent by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }

    DefaultScaffold(
        modifier = modifier,
        showOrbitals = true
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            EnhancedLottieAnimation(
                lottieFile = com.newton.common_ui.R.raw.innovators_success,
                animateEntry = true,
                iterations = 1,
                onAnimationFinish = {
                    animationState = LottieAnimationState.Completed
                    showContent = true
                }
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 500)
                ) + slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Account Created Successfully!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Check your email and click on the verification link to activate your account, Then proceed to login with your credentials",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            LaunchedEffect(showContent) {
                if (showContent) {
                    delay(500)
                    showButton = true
                }
            }

            AnimatedVisibility(
                visible = showButton,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 500)
                ) + expandVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                Button(
                    onClick = {
                        onContinueClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Proceed to Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}