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
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.newton.commonUi.ui.*
import com.newton.core.enums.*
import com.newton.settings.presentation.viewModel.*
import com.newton.sharedprefs.viewModel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackPressed: () -> Unit,
    appVersion: String = "1.0.0",
    viewModel: ThemeViewModel = hiltViewModel(),
    cacheManagerViewModel: CacheManagerViewModel = hiltViewModel(),
    onNotificationSettingsChanged: (Boolean) -> Unit,
    onPrivacyPolicyClicked: () -> Unit,
    onTermsOfServiceClicked: () -> Unit,
    onAboutUsClicked: () -> Unit,
    onHelpAndSupportClicked: () -> Unit,
    onAccountDeletionPolicyClicked: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val hapticFeedback = LocalHapticFeedback.current
    val themeState by viewModel.themeState.collectAsState()
    val cacheUiState by cacheManagerViewModel.uiState.collectAsState()

    var notificationsEnabled by remember { mutableStateOf(true) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showClearCacheDialog by remember { mutableStateOf(false) }
    var cacheCleared by remember { mutableStateOf(false) }

    LaunchedEffect(cacheUiState.cacheClearingSuccess) {
        if (cacheUiState.cacheClearingSuccess && cacheUiState.cacheClearingDetails != null) {
            cacheCleared = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        cacheManagerViewModel.refreshCacheSize()
    }

    CustomScaffold(
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
                    subtitle = when (themeState.themeMode) {
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
                    subtitle = if (cacheUiState.isCacheClearing) {
                        "Clearing cache..."
                    } else if (cacheUiState.cacheClearingSuccess) {
                        "Cache cleared"
                    } else {
                        "Current size: ${cacheUiState.cacheSize}"
                    },
                    onClick = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        showClearCacheDialog = true
                    },
                    trailingContent = when {
                        cacheUiState.isCacheClearing -> {
                            {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(end = 8.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                        }

                        cacheUiState.cacheClearingSuccess -> {
                            {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        else -> null
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
                    icon = Icons.Outlined.Delete,
                    title = "Account Deletion Policy",
                    onClick = onAccountDeletionPolicyClicked
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
            currentTheme = themeState.themeMode,
            onThemeSelected = { selectedTheme ->
                viewModel.handleEvent(ThemeEvent.SetThemeMode(selectedTheme))
                showThemeDialog = false
            },
            onDismiss = { showThemeDialog = false }
        )
    }

    if (showClearCacheDialog) {
        CustomDialog(
            title = "Clear Cache",
            onDismiss = { showClearCacheDialog = false },
            confirmButtonText = "Clear",
            onConfirm = {
                cacheManagerViewModel.clearCache()
                showClearCacheDialog = false
            },
            content = {
                Column {
                    Text(
                        "This will clear:",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text("• Temporary files")
                    Text("• Image cache")
                    Text("• Network responses")
                    Text(
                        "\nYour personal data, settings, and saved content will not be affected.",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        "\nCurrent cache size: ${cacheUiState.cacheSize}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        )
    }

    if (cacheCleared && cacheUiState.cacheClearingDetails != null) {
        CustomDialog(
            title = "Cache Cleared",
            onDismiss = {
                cacheCleared = false
                cacheManagerViewModel.resetCacheClearingSuccess()
            },
            confirmButtonText = "OK",
            dismissButtonText = "",
            onConfirm = {
                cacheCleared = false
                cacheManagerViewModel.resetCacheClearingSuccess()
            },
            content = {
                Text(
                    text = "Successfully cleared ${cacheUiState.cacheClearingDetails?.formattedSize} of cached data.",
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}
