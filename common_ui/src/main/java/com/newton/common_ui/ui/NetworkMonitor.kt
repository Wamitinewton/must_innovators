package com.newton.common_ui.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.newton.core.network.NetworkConfiguration
import com.newton.core.network.NetworkStatus
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NetworkMonitor(
    networkConfiguration: NetworkConfiguration,
    snackbarHostState: SnackbarHostState
) {
    var lastNetworkStatus by remember { mutableStateOf<NetworkStatus?>(null) }

    LaunchedEffect(Unit) {
        networkConfiguration.observeNetworkStatus().collectLatest { status ->
            if (lastNetworkStatus != status){
                when(status) {
                   NetworkStatus.NoInternet, NetworkStatus.Unavailable -> {
                       val message = networkConfiguration.getNetworkErrorMessage()
                       snackbarHostState.showSnackbar(
                           message = message,
                           duration = SnackbarDuration.Long
                       )
                   }
                    NetworkStatus.Available -> {
                        if (lastNetworkStatus == NetworkStatus.NoInternet ||
                            lastNetworkStatus == NetworkStatus.Unavailable) {
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