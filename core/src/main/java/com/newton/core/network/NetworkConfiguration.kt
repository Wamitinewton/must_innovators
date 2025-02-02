package com.newton.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NetworkConfiguration(private val context: Context) {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    /**
     * Monitors network connectivity changes and returns
     * a Flow of NetworkStatus
     */
    fun observeNetworkStatus(): Flow<NetworkStatus> = callbackFlow {
        val callBack = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (hasInternetConnection()) {
                    trySend(NetworkStatus.Available)
                } else {
                    trySend(NetworkStatus.NoInternet)
                }
            }

            override fun onLost(network: Network) {
                trySend(NetworkStatus.NoInternet)
            }

            override fun onUnavailable() {
                trySend(NetworkStatus.Unavailable)
            }
        }
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callBack)

        if (isNetworkAvailable()) {
            if (hasInternetConnection()) {
                trySend(NetworkStatus.Available)
            } else {
                trySend(NetworkStatus.NoInternet)
            }
        } else {
            trySend(NetworkStatus.Unavailable)
        }

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callBack)
        }
    }


    /**
     * Checks if device is connected to any network
     */
    private fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null
    }

    /**
     * Checks if the current connection is WIFI
     */
    private fun isWifiConnection(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }

    /**
     * Checks if the current connection is cellular
     */
    private fun isCellularConnection(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    }

    /**
     * Checks if there is actual internet connectivity
     * by making a lightweight request
     */
    private fun hasInternetConnection(): Boolean {
        return try {
            val url = URL("https://8.8.8.8") // Google DNS
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("User-Agent", "Android")
            connection.setRequestProperty("Connection", "Close")
            connection.connectTimeout = 1500
            connection.connect()
            connection.responseCode == 200
        } catch (e: IOException) {
            false
        }
    }

    /**
     * Get detailed network error message based on current status
     */
    fun getNetworkErrorMessage(): String {
        return when {
            !isNetworkAvailable() ->
                "No network connection available. Please check your device's network settings."
            isWifiConnection() && !hasInternetConnection() ->
                "Connected to WiFi but no internet access. Please check your WiFi connection."
            isCellularConnection() && !hasInternetConnection() ->
                "Connected to mobile data but no internet access. Please check your data connection."
            else -> "Unable to connect to the internet. Please try again later."
        }
    }
}
