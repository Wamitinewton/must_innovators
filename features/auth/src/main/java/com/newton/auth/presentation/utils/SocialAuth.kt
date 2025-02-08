package com.newton.auth.presentation.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.newton.common_ui.R

@Composable
fun SocialAuthentication(
    onGoogleLogin: () -> Unit,
    onGithubLogin: () -> Unit,
) {
    Column {
        Button(
            onClick = {
                onGoogleLogin()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE5B8E8)
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = R.drawable.google,
                    contentDescription = "Google",
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = "Continue with google"
                )
            }
        }

        Button(
            onClick = {
                onGithubLogin()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE5B8E8)
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = R.drawable.github,
                    contentDescription = "Github",
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = "Continue with github"
                )
            }
        }
    }
}