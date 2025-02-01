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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.auth.R
import com.newton.common_ui.ui.CButton
import com.newton.common_ui.ui.DefaultTextInput
import com.newton.meruinnovators.ui.theme.AlegreyaSansFontFamily

@Composable
fun SignupScreen() {
//    var firstName by remember { mutableStateOf("") }
//    var lastName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var userName by remember { mutableStateOf("") }
//    var registrationNo by remember { mutableStateOf("") }
//    var courseName by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }

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
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier
                   .fillMaxSize()
                   .padding(horizontal = 24.dp)
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
                   onInputChanged = {},
                   inputText = "",
                   hint = "first name",
                   onSubmitted = {}
               )
               DefaultTextInput(
                   onInputChanged = {},
                   inputText = "",
                   hint = "last name",
                   onSubmitted = {}
               )
               DefaultTextInput(
                   onInputChanged = {},
                   inputText = "",
                   hint = "email",
                   onSubmitted = {}
               )
               DefaultTextInput(
                   onInputChanged = {},
                   inputText = "",
                   hint = "user name",
                   onSubmitted = {}
               )
               DefaultTextInput(
                   onInputChanged = {},
                   inputText = "",
                   hint = "registration no",
                   onSubmitted = {}
               )
               DefaultTextInput(
                   onInputChanged = {},
                   inputText = "",
                   hint = "Course name",
                   onSubmitted = {}
               )
               DefaultTextInput(
                   onInputChanged = {},
                   inputText = "",
                   hint = "password",
                   onSubmitted = {}
               )
               DefaultTextInput(
                   onInputChanged = {},
                   inputText = "",
                   hint = "confirm password",
                   onSubmitted = {}
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