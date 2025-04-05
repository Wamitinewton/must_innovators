package com.newton.auth.presentation.signUp.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import kotlinx.coroutines.*

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
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            EnhancedLottieAnimation(
                lottieFile = com.newton.commonUi.R.raw.innovators_success,
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
                enter =
                fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 500)
                ) +
                    slideInVertically(
                        initialOffsetY = { 50 },
                        animationSpec =
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Account Verified Successfully!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Login in with your credentials to continue",
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
                enter =
                fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 500)
                ) +
                    expandVertically(
                        animationSpec =
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {
                Button(
                    onClick = {
                        onContinueClick()
                    },
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors =
                    ButtonDefaults.buttonColors(
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
