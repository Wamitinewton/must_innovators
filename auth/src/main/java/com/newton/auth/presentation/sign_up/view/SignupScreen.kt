package com.newton.auth.presentation.sign_up.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newton.auth.presentation.sign_up.viewmodel.SignupViewModel
import com.newton.auth.presentation.utils.SocialAuthentication
import com.newton.common_ui.R
import com.newton.common_ui.ui.CustomButton
import com.newton.common_ui.ui.DefaultTextInput
import com.newton.common_ui.ui.PasswordTextInput

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var registrationNo by remember { mutableStateOf("") }
    var courseName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


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

               DefaultTextInput(
                   onInputChanged = {
                       firstName = it
                   },
                   inputText = firstName,
                   label = "first name",
                   onSubmitted = {},
                   isError = true,
                   imeAction = ImeAction.Next
               )
               DefaultTextInput(
                   onInputChanged = {
                       lastName = it
                   },
                   inputText = lastName,
                   label = "last name",
                   onSubmitted = {},
                   isError = true,
                   imeAction = ImeAction.Next
               )
               DefaultTextInput(
                   onInputChanged = {
                       email = it
                   },
                   inputText = email,
                   label = "email",
                   onSubmitted = {},
                   imeAction = ImeAction.Next

               )
               DefaultTextInput(
                   onInputChanged = {
                       userName = it
                   },
                   inputText = userName,
                   label = "user name",
                   onSubmitted = {},
                   imeAction = ImeAction.Next
               )
               DefaultTextInput(
                   onInputChanged = {
                       registrationNo = it
                   },
                   inputText = registrationNo,
                   label = "registration no",
                   onSubmitted = {},
                   imeAction = ImeAction.Next
               )
               DefaultTextInput(
                   onInputChanged = {
                       courseName = it
                   },
                   inputText = courseName,
                   label = "Course name",
                   onSubmitted = {},
                   imeAction = ImeAction.Next
               )
               PasswordTextInput(
                   onValueChange = {
                       password = it
                   },
                   value = password,
                   label = "password",
                   imeAction = ImeAction.Next
               )
               PasswordTextInput(
                   onValueChange = {
                       confirmPassword = it
                   },
                   value = confirmPassword,
                   label = "confirm password",
                   imeAction = ImeAction.Next
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