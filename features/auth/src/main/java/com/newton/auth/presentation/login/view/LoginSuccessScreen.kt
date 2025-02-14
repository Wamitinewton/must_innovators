package com.newton.auth.presentation.login.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.login.event.GetUserDataEvent
import com.newton.auth.presentation.login.state.GetUserDataViewModelState
import com.newton.auth.presentation.login.view_model.GetUserDataViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    // Error effect
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = error,
                    duration = SnackbarDuration.Long,
                    withDismissAction = true
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
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
            modifier = Modifier
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
private fun LoadingAnimation(
    isLoading: Boolean
) {
    val dots = 3
    val dotSize = 24.dp
    val delayUnit = 200

    Row(
        modifier = Modifier
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
                modifier = Modifier
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
fun AnimatedSuccessMessage(
    state: GetUserDataViewModelState
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = state.isLoading,
        enter = fadeIn() + slideInVertically {
            with(density) { -40.dp.roundToPx() }
        },
        exit = fadeOut() + slideOutVertically {
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
        enter = fadeIn() + slideInVertically {
            with(density) { 40.dp.roundToPx() }
        }
    ) {
        Text(
            text = "Welcome back, ${state.userData?.username}!",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}