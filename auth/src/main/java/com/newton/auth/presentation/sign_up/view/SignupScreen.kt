package com.newton.auth.presentation.sign_up.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.common_ui.R
import com.newton.common_ui.ui.CButton
import com.newton.common_ui.ui.DefaultTextInput
import com.newton.common_ui.ui.PasswordTextInput
import com.newton.meruinnovators.ui.theme.AlegreyaSansFontFamily

@Composable
fun SignupScreen() {
    val scrollState = rememberScrollState()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var registrationNo by remember { mutableStateOf("") }
    var courseName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Error states
    var firstNameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var userNameError by remember { mutableStateOf<String?>(null) }
    var registrationError by remember { mutableStateOf<String?>(null) }
    var courseNameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
       Box(modifier = Modifier.fillMaxSize().padding(padding)) {
           Image(painter = painterResource(R.drawable.bg1),
               contentDescription = null,
               modifier = Modifier
                   .fillMaxWidth()
                   .height(190.dp)
                   .align(Alignment.BottomCenter)
               )

           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .verticalScroll(scrollState)
                   .padding(horizontal = 14.dp)
           ) {
               Image(painter = painterResource(id = R.drawable.logo),
                   contentDescription = null,
                   modifier = Modifier
                       .padding(top = 54.dp)
                       .height(100.dp)
                       .align(Alignment.Start)
                       .offset(x = (-20).dp)
               )

               Text(text = "Sign Up",
                   style = TextStyle(
                       fontSize = 28.sp,
                       fontFamily = AlegreyaSansFontFamily,
                       fontWeight = FontWeight(500),
                       color = Color.White
                   ),
                   modifier = Modifier.align(Alignment.Start)
               )

               Text(text = "Sign up now to get engaged in our community",
                   style = TextStyle(
                       fontSize = 20.sp,
                       fontFamily = AlegreyaSansFontFamily,
                       color = Color(0xB2FFFFFF)
                   ),
                   modifier = Modifier
                       .align(Alignment.Start)
                       .padding(bottom = 24.dp)
               )


               DefaultTextInput(
                   onInputChanged = {
                       firstName = it
                   },
                   inputText = firstName,
                   label = "first name",
                   onSubmitted = {},
                   isError = firstNameError != null,
                   imeAction = ImeAction.Next
               )
               DefaultTextInput(
                   onInputChanged = {
                       lastName = it
                   },
                   inputText = lastName,
                   label = "last name",
                   onSubmitted = {},
                   isError = lastNameError != null,
                   errorMessage = lastNameError,
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

               CButton(text = "Sign up")
           }
       }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun SignupScreenPreview() {
    SignupScreen()
}