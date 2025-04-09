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
package com.newton.auth.presentation.signUp.view

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import androidx.hilt.navigation.compose.*
import androidx.lifecycle.compose.*
import com.newton.auth.presentation.signUp.event.*
import com.newton.auth.presentation.signUp.view.composables.SignupContent
import com.newton.auth.presentation.signUp.viewmodel.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.core.network.*
import kotlinx.coroutines.*

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel = hiltViewModel(),
    onNavigateToOnBoarding: () -> Unit
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
            onBackClick = { onNavigateToOnBoarding() },
            context,
            snackbarHostState,
            scope
        )
    }
}
