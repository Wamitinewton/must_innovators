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
