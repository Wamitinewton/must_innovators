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
