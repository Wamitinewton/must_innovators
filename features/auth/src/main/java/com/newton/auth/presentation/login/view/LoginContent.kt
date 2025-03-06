package com.newton.auth.presentation.login.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.login.event.LoginEvent
import com.newton.auth.presentation.login.state.LoginViewModelState
import com.newton.auth.presentation.utils.AuthHeader
import com.newton.auth.presentation.utils.OrContinueWith
import com.newton.auth.presentation.utils.SocialAuthentication
import com.newton.common_ui.ui.CustomButton

@Composable
fun LoginContent(
    uiState: LoginViewModelState,
    onEvent: (LoginEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
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
            onGithubLogin = {},
            onGoogleLogin = {}
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 11.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forgot password?",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {  }
            )
        }
    }
}