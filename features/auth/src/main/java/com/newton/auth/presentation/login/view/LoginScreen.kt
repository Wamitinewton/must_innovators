package com.newton.auth.presentation.login.view

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.newton.auth.presentation.login.event.LoginEvent
import com.newton.auth.presentation.login.event.LoginNavigationEvent
import com.newton.auth.presentation.login.view_model.LoginViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.NetworkMonitor
import com.newton.navigation.NavigationRoutes
import com.newton.core.network.NetworkConfiguration
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navHostController: NavHostController,
    onForgotPasswordClick: () -> Unit,
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
                loginViewModel.onEvent(LoginEvent.ClearError)
            }
        }
    }

  DefaultScaffold(
      snackbarHostState = snackbarHostState,
      isLoading = uiState.isLoading,
      showOrbitals = true
  ){
      LoginContent(
          uiState = uiState,
          onEvent = loginViewModel::onEvent,
          onBackClick = { navHostController.navigate(NavigationRoutes.OnboardingRoute.routes) },
          onForgotPasswordClick = onForgotPasswordClick,
          onVerifyAccountClick = {}
      )
  }
}