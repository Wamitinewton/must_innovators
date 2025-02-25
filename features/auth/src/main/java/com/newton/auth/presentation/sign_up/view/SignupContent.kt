package com.newton.auth.presentation.sign_up.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.sign_up.event.SignupUiEvent
import com.newton.auth.presentation.sign_up.state.SignupViewmodelState
import com.newton.auth.presentation.utils.AuthHeader
import com.newton.auth.presentation.utils.OrContinueWith
import com.newton.auth.presentation.utils.SocialAuthentication
import com.newton.common_ui.ui.CustomTextLabelSmall
import com.newton.common_ui.ui.CustomButton

@Composable
fun SignupContent(
    uiState: SignupViewmodelState,
    onEvent: (SignupUiEvent) -> Unit,
    onBackClick: () -> Unit,
    navigateToEvents: () -> Unit = {},
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
                    it,
                ))
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
                CustomTextLabelSmall(bodyText = "Sign up")
            }
        )

    }
}


