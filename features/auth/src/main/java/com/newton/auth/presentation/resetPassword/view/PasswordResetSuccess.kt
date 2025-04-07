/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.auth.presentation.resetPassword.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import kotlinx.coroutines.*

@Composable
fun PasswordResetSuccess(onNavigateToLogin: () -> Unit) {
    val cardVisibleState =
        remember {
            MutableTransitionState(false).apply {
                targetState = true
            }
        }

    var showButton by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visibleState = cardVisibleState,
                enter =
                fadeIn(animationSpec = tween(500)) +
                    slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(500)
                    )
            ) {
                Card(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        EnhancedLottieAnimation(
                            lottieFile = com.newton.commonUi.R.raw.innovators_success
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Password Reset Successful!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Your password has been reset successfully. You can now log in with your new password.",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        AnimatedVisibility(
                            visible = showButton,
                            enter =
                            fadeIn(tween(500)) +
                                slideInVertically(
                                    initialOffsetY = { it / 2 },
                                    animationSpec = tween(500)
                                )
                        ) {
                            ElevatedButton(
                                onClick = onNavigateToLogin,
                                modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors =
                                ButtonDefaults.elevatedButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                elevation =
                                ButtonDefaults.elevatedButtonElevation(
                                    defaultElevation = 6.dp
                                )
                            ) {
                                Text(
                                    "Return to Login",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Show button after animation plays
    LaunchedEffect(Unit) {
        delay(1500)
        showButton = true
    }
}
