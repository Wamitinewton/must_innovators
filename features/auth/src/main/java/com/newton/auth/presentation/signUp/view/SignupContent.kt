package com.newton.auth.presentation.signUp.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.auth.presentation.signUp.event.*
import com.newton.auth.presentation.signUp.state.*
import com.newton.auth.presentation.utils.*
import com.newton.commonUi.ui.*

@Composable
fun SignupContent(
    uiState: SignupViewmodelState,
    onEvent: (SignupUiEvent) -> Unit,
    onBackClick: () -> Unit
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
            headerText = "Sign up"
        )

        SocialAuthentication(
            onGoogleLogin = {},
            onGithubLogin = {}
        )

        OrContinueWith()

        SignupForm(
            username = uiState.userName,
            onUsernameChanged = {
                onEvent(SignupUiEvent.UsernameChanged(it))
            },
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
                        it
                    )
                )
            },
            passwordError = uiState.passwordError,
            isPasswordError = uiState.passwordError != null,
            confirmPwdError = uiState.confirmPasswordError,
            isConfirmPwdError = uiState.confirmPasswordError != null
        )

        Spacer(modifier = Modifier.height(15.dp))

        CustomButton(
            onClick = {
                onEvent(SignupUiEvent.SignUp)
            },
            enabled = !uiState.isLoading,
            content = {
                Text(
                    text = "Sign up",
                    style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
    }
}
