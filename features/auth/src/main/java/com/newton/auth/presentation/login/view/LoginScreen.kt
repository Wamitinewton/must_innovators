package com.newton.auth.presentation.login.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.newton.auth.presentation.login.event.LoginNavigationEvent
import com.newton.auth.presentation.login.view_model.LoginViewModel
import com.newton.common_ui.composables.animation.custom_animations.OrbitalsBackground
import com.newton.common_ui.ui.LoadingDialog
import com.newton.common_ui.ui.NetworkMonitor
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.network.NetworkConfiguration
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navHostController: NavHostController,
) {
    val uiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val networkConfiguration = NetworkConfiguration(context)

    NetworkMonitor(
        networkConfiguration = networkConfiguration,
        snackbarHostState = snackbarHostState
    )

    LaunchedEffect(Unit) {
        loginViewModel.navigateToLoginSuccess.collect { event ->
            when(event) {
                LoginNavigationEvent.NavigateToLoginSuccess -> {
                    navHostController.navigate(NavigationRoutes.UserDataLoadingRoute.routes)
                }
            }
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = error,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Box(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.surfaceDim,
                                MaterialTheme.colorScheme.surfaceBright
                            )
                        )
                    )
            )
            OrbitalsBackground()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LoginContent(
                    uiState = uiState,
                    onEvent = loginViewModel::onEvent,
                    onBackClick = { navHostController.navigate(NavigationRoutes.OnboardingRoute.routes) },
                )
                if (uiState.isLoading) {
                    LoadingDialog()
                }
            }
        }
    }
}