package com.newton.auth.presentation.login.view

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import androidx.lifecycle.compose.*
import androidx.navigation.*
import com.newton.auth.presentation.login.event.*
import com.newton.auth.presentation.login.viewModel.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.core.network.*
import com.newton.navigation.*
import kotlinx.coroutines.*

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navHostController: NavHostController,
    onForgotPasswordClick: () -> Unit
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
            when (event) {
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
                loginViewModel.onEvent(LoginEvent.ClearError)
            }
        }
    }

    DefaultScaffold(
        snackbarHostState = snackbarHostState,
        isLoading = uiState.isLoading,
        showOrbitals = true
    ) {
        LoginContent(
            uiState = uiState,
            onEvent = loginViewModel::onEvent,
            onBackClick = { navHostController.navigate(NavigationRoutes.OnboardingRoute.routes) },
            onForgotPasswordClick = onForgotPasswordClick,
            onVerifyAccountClick = {}
        )
    }
}
