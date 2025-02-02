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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.auth.presentation.sign_up.event.SignupUiEvent
import com.newton.auth.presentation.sign_up.viewmodel.SignupViewModel
import com.newton.auth.presentation.utils.SocialAuthentication
import com.newton.common_ui.ui.CustomButton

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val uiState by signupViewModel.authUiState.collectAsStateWithLifecycle()



    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->

        Column(
               modifier = Modifier
                   .padding(padding)
                   .fillMaxSize()
                   .verticalScroll(scrollState)
                   .padding(horizontal = 14.dp),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {

           AuthHeader()

               SocialAuthentication(
                   onGoogleLogin = {},
                   onGithubLogin = {}
               )

               OrContinueWith()

             SignupForm(
                 firstName = uiState.firstNameInput!!,
                 onFirstnameChanged = {
                     signupViewModel.onEvent(SignupUiEvent.FirstNameChanged(it))
                 },
                 lastName = uiState.lastNameInput!!,
                 onLastnameChanged = {
                     signupViewModel.onEvent(SignupUiEvent.LastNameChanged(it))
                 },
                 email = uiState.emailInput!!,
                 onEmailChanged = {
                     signupViewModel.onEvent(SignupUiEvent.EmailChanged(it))
                 },
                 userName = uiState.userName!!,
                 onUsernameChanged = {
                     signupViewModel.onEvent(SignupUiEvent.UsernameChanged(it))
                 },
                 registrationNo = uiState.registrationNo!!,
                 onRegistrationNoChanged = {
                     signupViewModel.onEvent(SignupUiEvent.RegistrationNoChanged(it))
                 },
                 courseName = uiState.courseName!!,
                 onCourseNameChanged = {
                     signupViewModel.onEvent(SignupUiEvent.CourseChanged(it))
                 },
                 password = uiState.passwordInput!!,
                 onPasswordChanged = {
                     signupViewModel.onEvent(SignupUiEvent.PasswordChanged(it))
                 },
                 confirmPassword = uiState.confirmPassword!!,
                 onConfirmPasswordChanged = {
                     signupViewModel.onEvent(SignupUiEvent.ConfirmPasswordChanged(it))
                 }
             )

               Spacer(modifier = Modifier.height(24.dp))

               CustomButton(text = "Sign up")
           }
       }
    }


@Composable
fun AuthHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp, top = 30.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {}
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


@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun SignupScreenPreview() {
    SignupScreen()
}