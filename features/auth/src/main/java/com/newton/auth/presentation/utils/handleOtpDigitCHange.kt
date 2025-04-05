package com.newton.auth.presentation.utils

import androidx.compose.ui.focus.*
import kotlinx.coroutines.*

fun handleOtpDigitChange(
    index: Int,
    newValue: String,
    currentOtp: String,
    onOtpChanged: (String) -> Unit,
    focusRequesters: List<FocusRequester>,
    coroutineScope: CoroutineScope,
    focusManager: androidx.compose.ui.focus.FocusManager
) {
    when {
        newValue.isEmpty() -> {
            val newOtp =
                if (index < currentOtp.length) {
                    currentOtp.substring(0, index) + currentOtp.substring(index + 1)
                } else {
                    currentOtp.substring(0, index.coerceAtMost(currentOtp.length))
                }
            onOtpChanged(newOtp)

            if (index > 0) {
                coroutineScope.launch {
                    focusRequesters[index - 1].requestFocus()
                }
            }
        }

        newValue.length == 1 && newValue.matches(Regex("\\d")) -> {
            // Handle single digit input
            val newOtp =
                buildString {
                    append(currentOtp.take(index))
                    append(newValue)
                    if (index < currentOtp.length) {
                        append(currentOtp.substring(index + 1))
                    }
                }
            onOtpChanged(newOtp)

            if (index < 5) {
                coroutineScope.launch {
                    focusRequesters[index + 1].requestFocus()
                }
            } else {
                focusManager.clearFocus()
            }
        }

        newValue.length > 1 && newValue.all { it.isDigit() } -> {
            val digitsToUse = newValue.take(6 - index)
            val newOtp =
                currentOtp.take(index) + digitsToUse + currentOtp.drop(index + digitsToUse.length)
            onOtpChanged(newOtp.take(6))

            val nextFocusIndex = (index + digitsToUse.length).coerceAtMost(5)
            coroutineScope.launch {
                if (nextFocusIndex < 6) {
                    focusRequesters[nextFocusIndex].requestFocus()
                } else {
                    focusManager.clearFocus()
                }
            }
        }
    }
}
