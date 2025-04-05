package com.newton.auth.presentation.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*

@Composable
fun AuthHeader(
    onBackButtonClick: () -> Unit,
    headerText: String
) {
    Row(
        modifier =
        Modifier
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
            text = headerText,
            style =
            MaterialTheme.typography.headlineMedium.copy(
                fontSize = 18.sp
            )
        )
    }
}

@Composable
fun OrContinueWith() {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier =
            Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Text("Or continue with email")
        HorizontalDivider(
            modifier =
            Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}
