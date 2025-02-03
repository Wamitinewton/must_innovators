package com.newton.auth.presentation.sign_up.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.auth.presentation.sign_up.event.SignupUiEvent
import com.newton.auth.presentation.sign_up.state.SignupViewmodelState
import com.newton.auth.presentation.utils.SocialAuthentication
import com.newton.common_ui.ui.CustomButton

@Composable
fun SignupContent(
    uiState: SignupViewmodelState,
    onEvent: (SignupUiEvent) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AuthHeader(
            onBackButtonClick = onBackClick
        )

        SocialAuthentication(
            onGoogleLogin = {},
            onGithubLogin = {}
        )

        OrContinueWith()

        SignupForm(
            firstName = uiState.firstNameInput,
            onFirstnameChanged = {
                onEvent(SignupUiEvent.FirstNameChanged(it))
            },
            lastName = uiState.lastNameInput,
            onLastnameChanged = {
                onEvent(SignupUiEvent.LastNameChanged(it))
            },
            email = uiState.emailInput,
            onEmailChanged = {
                onEvent(SignupUiEvent.EmailChanged(it))
            },
            emailError = uiState.emailError,
            isEmailError = uiState.emailError != null,
            courseName = uiState.courseName,
            onCourseNameChanged = {
                onEvent(SignupUiEvent.CourseChanged(it))
            },
            password = uiState.passwordInput,
            onPasswordChanged = {
                onEvent(SignupUiEvent.PasswordChanged(it))
            },
            confirmPassword = uiState.confirmPassword,
            onConfirmPasswordChanged = {
                onEvent(
                    SignupUiEvent.ConfirmPasswordChanged(
                    it,
                ))
            },
            passwordError = uiState.passwordError,
            isPasswordError = uiState.passwordError != null,
            confirmPwdError = uiState.confirmPasswordError,
            isConfirmPwdError = uiState.confirmPasswordError != null
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Sign up",
            onClick = {
                onEvent(SignupUiEvent.SignUp)
            },
            enabled = !uiState.isLoading
        )
    }
}

@Composable
fun AuthHeader(
    onBackButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp, top = 30.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onBackButtonClick()
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.width(30.dp))
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 18.sp,
            ),
        )
    }
}

@Composable
fun OrContinueWith() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Text("Or continue with email")
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}
