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
package com.newton.auth.presentation.resetPassword.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    email: String,
    isLoading: Boolean,
    emailError: String? = null,
    onEmailChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onBackPressed: () -> Unit,
    otpError: String? = null,
    topBarTitle: String,
    screenTitle: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }

    val gradientColors =
        listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceDim,
            MaterialTheme.colorScheme.surfaceBright
        )

    LaunchedEffect(otpError) {
        otpError?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Long
            )
        }
    }

    DefaultScaffold(
        snackbarHostState = snackbarHostState,
        isLoading = isLoading,
        topBar = {
            TopAppBar(
                title = {
                    Text(topBarTitle)
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier =
                        Modifier
                            .padding(bottom = 16.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                brush = Brush.linearGradient(gradientColors),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = screenTitle,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Text(
                        text = screenTitle,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Enter your email address to receive a verification code",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    DefaultTextField(
                        value = email,
                        onValueChange = onEmailChanged,
                        label = "Email",
                        isError = emailError != null,
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true,
                        leadingIcon = Icons.Default.Email,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            onSubmit()
                        },
                        enabled = !isLoading,
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                        contentPadding = ButtonDefaults.ContentPadding
                    ) {
                        Box(
                            modifier =
                            Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Send Code",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
