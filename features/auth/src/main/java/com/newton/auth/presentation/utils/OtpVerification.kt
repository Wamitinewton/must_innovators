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

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    otp: String,
    isLoading: Boolean,
    otpError: String?,
    email: String,
    onOtpChanged: (String) -> Unit,
    onResendOtp: () -> Unit,
    onVerifyOtp: () -> Unit,
    resendOtpError: String?,
    onBackPressed: () -> Unit
) {
    var remainingSeconds by remember { mutableIntStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(true) }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val focusStates = remember { List(6) { mutableStateOf(false) } }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = isTimerRunning) {
        while (isTimerRunning && remainingSeconds > 0) {
            delay(1000L)
            remainingSeconds--
            if (remainingSeconds == 0) {
                isTimerRunning = false
            }
        }
    }

    LaunchedEffect(resendOtpError) {
        resendOtpError?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Long
            )
        }
    }

    LaunchedEffect(otp) {
        val nextEmptyIndex = otp.length.coerceAtMost(5)
        if (nextEmptyIndex < 6) {
            focusRequesters[nextEmptyIndex].requestFocus()
        } else {
            keyboardController?.hide()
        }
    }

    LaunchedEffect(otp) {
        if (otp.length == 6 && !isLoading) {
            delay(300)
            onVerifyOtp()
        }
    }

    DefaultScaffold(
        snackbarHostState = snackbarHostState,
        isLoading = isLoading,
        showOrbitals = true,
        topBar = {
            TopAppBar(
                title = { /* Empty title */ },
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
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verification Code",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "We've sent a verification code to",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Text(
                text = email,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 36.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (i in 0 until 6) {
                    OtpDigitBox(
                        value = otp.getOrNull(i)?.toString() ?: "",
                        isFocused = focusStates[i].value,
                        focusRequester = focusRequesters[i],
                        onValueChanged = { newValue ->
                            handleOtpDigitChange(
                                index = i,
                                newValue = newValue,
                                currentOtp = otp,
                                onOtpChanged = onOtpChanged,
                                focusRequesters = focusRequesters,
                                coroutineScope = coroutineScope,
                                focusManager = focusManager
                            )
                        },
                        onKeyEvent = { event ->
                            if (event.key == Key.Backspace && otp.getOrNull(i) == null && i > 0) {
                                val newOtp = otp.dropLast(1)
                                onOtpChanged(newOtp)
                                coroutineScope.launch {
                                    focusRequesters[i - 1].requestFocus()
                                }
                                true
                            } else {
                                false
                            }
                        },
                        onFocusChanged = { isFocused ->
                            focusStates[i].value = isFocused
                        }
                    )
                }
            }

            AnimatedVisibility(
                visible = otpError != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = otpError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Verify button
            ElevatedButton(
                onClick = onVerifyOtp,
                enabled = !isLoading && otp.length == 6,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors =
                ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Verify",
                        fontSize = (16.sp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Didn't receive the code? ",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (remainingSeconds > 0) {
                    Text(
                        text = "Wait ${remainingSeconds}s",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                } else {
                    Text(
                        text = "Resend",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier =
                        Modifier
                            .padding(start = 4.dp)
                            .border(
                                width = 0.dp,
                                color = Color.Transparent,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(4.dp)
                            .clickableWithRipple {
                                onResendOtp()
                                remainingSeconds = 60
                                isTimerRunning = true
                            },
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

    // Focus on first box when screen appears
    DisposableEffect(Unit) {
        coroutineScope.launch {
            delay(300)
            if (otp.isEmpty()) {
                focusRequesters[0].requestFocus()
            }
        }
        onDispose { }
    }
}
