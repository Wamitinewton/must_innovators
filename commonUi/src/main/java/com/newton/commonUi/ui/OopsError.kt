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
package com.newton.commonUi.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.R

@Composable
fun OopsError(
    modifier: Modifier = Modifier,
    errorMessage: String,
    buttonText: String = "Try Again",
    showButton: Boolean = false,
    onClick: (() -> Unit)? = null,
    imageHeight: Dp = 200.dp
) {
    require(!showButton || onClick != null) {
        "onClick must be provided when showButton is true"
    }
    Box(
        modifier = Modifier
            .padding(12.dp)
            .then(modifier)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(R.drawable.oopsy), contentDescription = null)
            Spacer(Modifier.height(12.dp))
            Text(
                errorMessage,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(12.dp))
            if (showButton && onClick != null) {
                CustomElevatedButton(
                    onClick = onClick,
                    content = {
                        Text(
                            buttonText,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
        }
    }
}
