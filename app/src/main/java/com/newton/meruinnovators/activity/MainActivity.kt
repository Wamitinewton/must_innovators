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
package com.newton.meruinnovators.activity

import android.*
import android.content.*
import android.content.pm.*
import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.core.content.*
import androidx.navigation.compose.rememberNavController
import com.newton.auth.data.workManager.*
import com.newton.meruinnovators.navigation.*
import com.newton.notifications.manager.*
import dagger.hilt.android.*
import javax.inject.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationSubGraphs: NavigationSubGraphs

    @Inject
    lateinit var notificationsManager: NotificationsManager


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                notificationsManager.initialize()
            }

            notificationsManager.checkNotificationPermission()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scheduleTokenRefreshWork(applicationContext)

        setupBackHandler()

        requestNotificationPermission()

        handleNotificationIntent(intent)

        setContent {
            val navController = rememberNavController()
            RootScreen(navigationSubGraphs, navController)
        }
    }

    // This handles the back press for both legacy and modern Android versions
    private fun setupBackHandler() {
        if (Build.VERSION.SDK_INT >= 33) {
            // Modern back handler will work with enableOnBackInvokedCallback="true" in manifest
        } else {
            // Legacy back handler
            onBackPressedDispatcher.addCallback(this) {
                // Your back press logic here
                // If you want default behavior, just call isEnabled = false before finish()
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent) {
        // Check if the app was opened from a notification
        if (intent.getBooleanExtra("fromNotification", false)) {
            val eventId = intent.getStringExtra("eventId")
            // Navigate to the appropriate screen based on eventId
            // You can use your navigationSubGraphs here for navigation
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
                PackageManager.PERMISSION_GRANTED -> {
                    notificationsManager.initialize()
                }

                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            notificationsManager.initialize()
        }
    }
}
