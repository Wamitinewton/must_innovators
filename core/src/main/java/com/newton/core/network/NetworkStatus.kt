package com.newton.core.network

sealed class NetworkStatus {
    data object Available : NetworkStatus()

    data object Unavailable : NetworkStatus()

    data object NoInternet : NetworkStatus()

    data class Error(val message: String) : NetworkStatus()
}
