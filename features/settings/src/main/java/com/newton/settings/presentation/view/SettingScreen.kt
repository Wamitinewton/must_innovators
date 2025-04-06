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
package com.newton.settings.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.hapticfeedback.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.composables.*
import com.newton.core.enums.*
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackPressed: () -> Unit,
    appVersion: String = "1.0.0",
    initialThemeMode: ThemeMode = ThemeMode.SYSTEM,
    onThemeChanged: (ThemeMode) -> Unit,
    onClearCache: () -> Unit,
    onNotificationSettingsChanged: (Boolean) -> Unit,
    onPrivacyPolicyClicked: () -> Unit,
    onTermsOfServiceClicked: () -> Unit,
    onAboutUsClicked: () -> Unit,
    onHelpAndSupportClicked: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val hapticFeedback = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()

    var themeMode by remember { mutableStateOf(initialThemeMode) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showClearCacheDialog by remember { mutableStateOf(false) }
    var cacheCleared by remember { mutableStateOf(false) }

    DefaultScaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSectionHeader(title = "Display")
            }
            item {
                SettingsItem(
                    icon = Icons.Outlined.DarkMode,
                    title = "Theme",
                    subtitle = when (themeMode) {
                        ThemeMode.LIGHT -> "Light"
                        ThemeMode.DARK -> "Dark"
                        ThemeMode.SYSTEM -> "System default"
                    },
                    onClick = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        showThemeDialog = true
                    }
                )
            }

            item {
                SettingsSectionHeader(title = "Preferences")
            }

            item {
                SettingsSwitch(
                    icon = Icons.Outlined.Notifications,
                    title = "Notifications",
                    subtitle = "Enable push notifications",
                    checked = notificationsEnabled,
                    onCheckedChange = { isChecked ->
                        notificationsEnabled = isChecked
                        onNotificationSettingsChanged(isChecked)
                    }
                )
            }

            item {
                SettingsSectionHeader(title = "Storage & Data")
            }

            item {
                SettingsItem(
                    icon = Icons.Outlined.DeleteSweep,
                    title = "Clear cache",
                    subtitle = if (cacheCleared) "Cache cleared" else "Free up storage space",
                    onClick = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        showClearCacheDialog = true
                    },
                    trailingContent = if (cacheCleared) {
                        {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        null
                    }
                )
            }

            item {
                SettingsSectionHeader(title = "About & Legal")
            }

            item {
                SettingsItem(
                    icon = Icons.Outlined.PrivacyTip,
                    title = "Privacy Policy",
                    onClick = onPrivacyPolicyClicked
                )
            }

            item {
                SettingsItem(
                    icon = Icons.Outlined.Description,
                    title = "Terms of Service",
                    onClick = onTermsOfServiceClicked
                )
            }

            item {
                SettingsItem(
                    icon = Icons.Outlined.Info,
                    title = "About Us",
                    onClick = onAboutUsClicked
                )
            }

            item {
                SettingsItem(
                    icon = Icons.Outlined.SupportAgent,
                    title = "Help & Support",
                    onClick = onHelpAndSupportClicked
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "App Version $appVersion",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }

    if (showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = themeMode,
            onThemeSelected = { selectedTheme ->
                themeMode = selectedTheme
                onThemeChanged(selectedTheme)
                showThemeDialog = false
            },
            onDismiss = { showThemeDialog = false }
        )
    }

    if (showClearCacheDialog) {
        AlertDialog(
            onDismissRequest = { showClearCacheDialog = false },
            icon = { Icon(Icons.Outlined.DeleteSweep, contentDescription = null) },
            title = { Text("Clear Cache") },
            text = { Text("This will clear all temporary files and cached data. This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        onClearCache()
                        cacheCleared = true
                        showClearCacheDialog = false
                        coroutineScope.launch {
                            delay(2000)
                            cacheCleared = false
                        }
                    }
                ) {
                    Text("Clear")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showClearCacheDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
