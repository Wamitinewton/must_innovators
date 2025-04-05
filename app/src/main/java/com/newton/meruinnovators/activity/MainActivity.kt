package com.newton.meruinnovators.activity

import android.*
import android.content.*
import android.content.pm.*
import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.core.content.*
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
            } else {
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
            RootScreen(navigationSubGraphs)
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
