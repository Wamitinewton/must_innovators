package com.newton.auth.presentation.sign_up.view

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.newton.auth.presentation.sign_up.event.SignUpNavigationEvent
import com.newton.auth.presentation.sign_up.event.SignupUiEvent
import com.newton.auth.presentation.sign_up.viewmodel.SignupViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.NetworkMonitor
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.network.NetworkConfiguration
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val uiState by signupViewModel.authUiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val networkConfiguration = NetworkConfiguration(context)

    NetworkMonitor(
        networkConfiguration = networkConfiguration,
        snackbarHostState = snackbarHostState
    )

    LaunchedEffect(Unit) {
        signupViewModel.navigateToLogin.collect { event ->
            when(event) {
                SignUpNavigationEvent.NavigateToSuccess -> {
                    navHostController.navigate(NavigationRoutes.SignupSuccessRoute.routes)
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
                signupViewModel.onEvent(SignupUiEvent.ClearError)
            }
        }
    }

    DefaultScaffold(
        snackbarHostState = snackbarHostState,
        isLoading = uiState.isLoading,
        showOrbitals = true
    ) {
        SignupContent(
            uiState = uiState,
            onEvent = signupViewModel::onEvent,
            onBackClick = { navHostController.navigate(NavigationRoutes.OnboardingRoute.routes) }
        )
    }
}