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
package com.newton.admin.presentation.events.view.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.R
import com.newton.commonUi.ui.*
import kotlinx.coroutines.*

@Composable
fun AdminSuccessScreen(onFinish: () -> Unit, text: String) {
    var showText by remember { mutableStateOf(false) }

    LaunchedEffect(showText) {
        if (showText) {
            delay(3000)
            onFinish()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        SuccessLottieAnimation(
            lottieFile = R.raw.innovators_success,
            onAnimationComplete = {
                showText = true
            }
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        AnimatedVisibility(
            visible = showText,
            enter = fadeIn() + scaleIn(
                initialScale = 0.8f,
                animationSpec = spring(
                    dampingRatio = 0.5f,
                    stiffness = Spring.StiffnessLow
                )
            ),
            exit = fadeOut() + scaleOut(
                targetScale = 1.1f,
                animationSpec = spring(
                    dampingRatio = 0.5f,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
