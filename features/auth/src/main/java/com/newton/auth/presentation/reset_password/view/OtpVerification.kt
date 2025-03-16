package com.newton.auth.presentation.reset_password.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.auth.presentation.utils.handleOtpDigitChange
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.OtpDigitBox
import com.newton.common_ui.ui.clickableWithRipple
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    // State variables
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

    // Show error in snackbar if any
    LaunchedEffect(resendOtpError) {
        resendOtpError?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Long
            )
        }
    }

    // Request focus for the first empty OTP box
    LaunchedEffect(otp) {
        val nextEmptyIndex = otp.length.coerceAtMost(5)
        if (nextEmptyIndex < 6) {
            focusRequesters[nextEmptyIndex].requestFocus()
        } else {
            keyboardController?.hide()
        }
    }

    // Auto-verification when OTP is complete
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.elevatedButtonColors(
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
                        modifier = Modifier
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