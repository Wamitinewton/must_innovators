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

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.auth.presentation.login.event.*
import com.newton.auth.presentation.login.state.*
import com.newton.auth.presentation.utils.*
import com.newton.commonUi.ui.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginContent(
    uiState: LoginViewModelState,
    onEvent: (LoginEvent) -> Unit,
    onBackClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onVerifyAccountClick: () -> Unit,
    scope: CoroutineScope,
    state: SnackbarHostState
) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthHeader(
            onBackButtonClick = onBackClick,
            headerText = "Log In"
        )

        SocialAuthentication(
            onGithubLogin = {
                scope.launch {
                    state.showSnackbar(
                        message = "Upcoming Feature",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onGoogleLogin = {
                scope.launch {
                    state.showSnackbar(
                        message = "Upcoming Feature",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        )

        OrContinueWith()

        LoginForm(
            email = uiState.emailInput,
            onEmailChanged = {
                onEvent(LoginEvent.EmailChanged(it))
            },
            emailError = uiState.emailError,
            isEmailError = uiState.emailError != null,
            password = uiState.passwordInput,
            onPasswordChanged = {
                onEvent(LoginEvent.PasswordChanged(it))
            },
            isPasswordError = uiState.passwordError != null,
            passwordError = uiState.passwordError
        )

        Spacer(modifier = Modifier.height(15.dp))

        CustomButton(
            onClick = {
                onEvent(LoginEvent.Login)
            },
            enabled = !uiState.isLoading,
            content = {
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp)
//                .background(
//                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
//                    shape = MaterialTheme.shapes.small
//                )
//                .border(
//                    width = 1.dp,
//                    brush = Brush.horizontalGradient(
//                        listOf(
//                            MaterialTheme.colorScheme.primary,
//                            MaterialTheme.colorScheme.tertiary
//                        )
//                    ),
//                    shape = MaterialTheme.shapes.small
//                )
//                .clickable(onClick = onVerifyAccountClick)
//                .padding(vertical = 12.dp, horizontal = 16.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Complete your registration! Verify your account now",
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    fontWeight = FontWeight.SemiBold,
//                    textAlign = TextAlign.Center
//                ),
//                color = MaterialTheme.colorScheme.primary
//            )
//        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forgot password?",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = onForgotPasswordClick)
            )
        }
    }
}
