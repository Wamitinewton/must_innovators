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
package com.newton.auth.presentation.login.view

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import androidx.lifecycle.compose.*
import androidx.navigation.*
import com.newton.auth.presentation.login.event.*
import com.newton.auth.presentation.login.view.composables.*
import com.newton.auth.presentation.login.viewModel.*
import com.newton.commonUi.ui.*
import com.newton.core.network.*
import com.newton.navigation.*
import kotlinx.coroutines.*

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navHostController: NavHostController,
    onForgotPasswordClick: () -> Unit,
    onNavigateToOnboarding: () -> Unit
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

    CustomScaffold(
        snackbarHostState = snackbarHostState,
        isLoading = uiState.isLoading
    ) {
        LoginContent(
            uiState = uiState,
            onEvent = loginViewModel::onEvent,
            onBackClick = onNavigateToOnboarding,
            onForgotPasswordClick = onForgotPasswordClick,
            onVerifyAccountClick = {},
            state = snackbarHostState,
            scope = scope
        )
    }
}
