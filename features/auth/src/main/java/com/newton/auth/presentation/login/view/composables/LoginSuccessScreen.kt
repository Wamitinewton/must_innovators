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
package com.newton.auth.presentation.login.view.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.auth.presentation.login.event.*
import com.newton.auth.presentation.login.state.*
import com.newton.auth.presentation.login.viewModel.*
import kotlinx.coroutines.*

@Composable
fun UserDataLoadingScreen(
    viewModel: GetUserDataViewModel,
    onNavigateToHome: () -> Unit
) {
    val state by viewModel.getUserDataState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.userData) {
        if (state.userData != null) {
            delay(1000)
            onNavigateToHome()
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = error,
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = false
                )
            }
        }
    }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(
                        onClick = {
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                viewModel.onEvent(GetUserDataEvent.GetUserEvent)
                            }
                        }
                    ) {
                        Text("RETRY", color = MaterialTheme.colorScheme.primary)
                    }
                }
            ) {
                Text(data.visuals.message)
            }
        }

        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoadingAnimation(isLoading = state.isLoading)

            Spacer(modifier = Modifier.height(32.dp))

            AnimatedSuccessMessage(state = state)
        }
    }
}

@Composable
private fun LoadingAnimation(isLoading: Boolean) {
    val dots = 3
    val dotSize = 24.dp
    val delayUnit = 200

    Row(
        modifier =
        Modifier
            .height(dotSize)
            .animateContentSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dots) { index ->
            val scale = remember { androidx.compose.animation.core.Animatable(0f) }

            LaunchedEffect(isLoading) {
                if (isLoading) {
                    delay(index * delayUnit.toLong())
                    while (true) {
                        scale.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(500)
                        )
                        scale.animateTo(
                            targetValue = 0.3f,
                            animationSpec = tween(500)
                        )
                        delay(dots * delayUnit.toLong())
                    }
                } else {
                    scale.stop()
                    scale.snapTo(0f)
                }
            }

            Box(
                modifier =
                Modifier
                    .padding(horizontal = 4.dp)
                    .size(dotSize)
                    .scale(scale.value)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun AnimatedSuccessMessage(state: GetUserDataViewModelState) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = state.isLoading,
        enter =
        fadeIn() +
            slideInVertically {
                with(density) { -40.dp.roundToPx() }
            },
        exit =
        fadeOut() +
            slideOutVertically {
                with(density) { 40.dp.roundToPx() }
            }
    ) {
        Text(
            text = "Loading your profile...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 16.dp)
        )
    }

    AnimatedVisibility(
        visible = state.userData != null,
        enter =
        fadeIn() +
            slideInVertically {
                with(density) { 40.dp.roundToPx() }
            }
    ) {
        Text(
            text = "Welcome back, ${state.userData?.username}!",
            style =
            MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
