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

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.newton.core.network.*
import kotlinx.coroutines.flow.*

@Composable
fun NetworkMonitor(
    networkConfiguration: NetworkConfiguration,
    snackbarHostState: SnackbarHostState
) {
    var lastNetworkStatus by remember { mutableStateOf<NetworkStatus?>(null) }

    LaunchedEffect(Unit) {
        networkConfiguration.observeNetworkStatus().collectLatest { status ->
            if (lastNetworkStatus != status) {
                when (status) {
                    NetworkStatus.NoInternet, NetworkStatus.Unavailable -> {
                        val message = networkConfiguration.getNetworkErrorMessage()
                        snackbarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Long
                        )
                    }

                    NetworkStatus.Available -> {
                        if (lastNetworkStatus == NetworkStatus.NoInternet ||
                            lastNetworkStatus == NetworkStatus.Unavailable
                        ) {
                            snackbarHostState.showSnackbar(
                                message = "Network connection restored",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                    is NetworkStatus.Error -> TODO()
                }
                lastNetworkStatus = status
            }
        }
    }
}
