package com.newton.auth.presentation.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import com.newton.commonUi.R

@Composable
fun SocialAuthentication(
    onGoogleLogin: () -> Unit,
    onGithubLogin: () -> Unit
) {
    Column {
        Button(
            onClick = {
                onGoogleLogin()
            },
            modifier =
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 12.dp),
            colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
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
                    text = "Continue with google",
                    style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        Button(
            onClick = {
                onGithubLogin()
            },
            modifier =
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 12.dp),
            colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
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
                    text = "Continue with github",
                    style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}
