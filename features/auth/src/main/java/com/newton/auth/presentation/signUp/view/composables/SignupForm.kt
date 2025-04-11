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
package com.newton.auth.presentation.signUp.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.*
import com.newton.auth.presentation.signUp.state.*
import com.newton.commonUi.ui.*

@Composable
fun SignupForm(
    username: String,
    onUsernameChanged: (String) -> Unit,
    firstName: String,
    onFirstnameChanged: (String) -> Unit,
    lastName: String,
    onLastnameChanged: (String) -> Unit,
    email: String,
    onEmailChanged: (String) -> Unit,
    emailError: String?,
    isEmailError: Boolean = false,
    courseName: String,
    onCourseNameChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    passwordError: String?,
    isPasswordError: Boolean = false,
    confirmPassword: String,
    onConfirmPasswordChanged: (String) -> Unit,
    confirmPwdError: String?,
    isConfirmPwdError: Boolean = false,
    isChecked: Boolean,
    onTermsClicked: () -> Unit,
    onPolicyClicked: () -> Unit,
    onCheckedClicked: () -> Unit,
    uiState: SignupViewmodelState
) {
    Column {
        val focusManager = LocalFocusManager.current
        AuthTextFields(
            onInputChanged = {
                onFirstnameChanged(it)
            },
            inputText = firstName,
            label = "first name",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) },
            supportingText = {
                uiState.firstNameError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium) }
            }
        )
        AuthTextFields(
            onInputChanged = {
                onLastnameChanged(it)
            },
            inputText = lastName,
            label = "last name",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) },
            supportingText = {
                uiState.lastNameError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium) }
            }
        )
        AuthTextFields(
            onInputChanged = {
                onUsernameChanged(it)
            },
            inputText = username,
            label = "Username",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) },
            supportingText = {
                uiState.usernameError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium) }
            }
        )
        AuthTextFields(
            onInputChanged = {
                onEmailChanged(it)
            },
            isError = isEmailError,
            errorMessage = emailError,
            inputText = email,
            label = "email",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) },
            supportingText = {
                uiState.emailError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium) }
            }
        )
        AuthTextFields(
            onInputChanged = {
                onCourseNameChanged(it)
            },
            inputText = courseName,
            label = "Course name",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) },
            supportingText = {
                uiState.courseError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium) }
            }
        )
        PasswordTextInput(
            value = password,
            onValueChange = {
                onPasswordChanged(it)
            },
            label = "password",
            isError = isPasswordError,
            imeAction = ImeAction.Next,
            supportingText = {
                uiState.passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium) }
            }
        )
        PasswordTextInput(
            value = confirmPassword,
            onValueChange = {
                onConfirmPasswordChanged(it)
            },
            label = "confirm password",
            isError = isConfirmPwdError,
            imeAction = ImeAction.Next,
            supportingText = {
                uiState.confirmPasswordError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium) }
            }
        )
        TermsCheckboxRow(isChecked, onTermsClicked, onPolicyClicked, onCheckedClicked)
    }
}
