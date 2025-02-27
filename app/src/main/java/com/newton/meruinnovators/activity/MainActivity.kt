package com.newton.meruinnovators.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.newton.auth.data.work_manager.scheduleTokenRefreshWork
import com.newton.meruinnovators.navigation.NavigationSubGraphs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, notifications can be scheduled
        } else {
            // Permission denied, consider informing the user
        }
    }

    @Inject
    lateinit var navigationSubGraphs: NavigationSubGraphs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNotificationPermission()
        handleNotificationIntent(intent)
        scheduleTokenRefreshWork(applicationContext)
        setContent {
            RootScreen(navigationSubGraphs)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent) {
        if (intent.hasExtra("action") && intent.getStringExtra("action") == "open_ticket") {
            val ticketId = intent.getStringExtra("ticketId")
            if (ticketId != null) {
                // Navigate to ticket details
                // navigationController.navigate("ticketDetails/$ticketId")
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Show rationale if needed
                    // showPermissionRationale()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }


}
