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
package com.newton.auth.presentation.signUp.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.core.enums.*


@Composable
fun NavigationButtons(
    currentStep: SignupStep,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onSubmit: () -> Unit,
    isLoading: Boolean,
    isSubmitEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (currentStep != SignupStep.PERSONAL_INFO) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier.weight(1f),
                enabled = !isLoading
            ) {
                Text(text = "Previous")
            }
        }

        when (currentStep) {
            SignupStep.CONFIRMATION -> {
                Button(
                    onClick = onSubmit,
                    modifier = Modifier.weight(if (currentStep == SignupStep.PERSONAL_INFO) 1f else 1f),
                    enabled = isSubmitEnabled
                ) {
                    Text(text = "Create Account")
                }
            }
            else -> {
                Button(
                    onClick = onNext,
                    modifier = Modifier.weight(if (currentStep == SignupStep.PERSONAL_INFO) 1f else 1f),
                    enabled = !isLoading
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}
