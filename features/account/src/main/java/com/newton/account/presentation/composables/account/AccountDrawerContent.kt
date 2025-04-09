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
package com.newton.account.presentation.composables.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.commonUi.R
import kotlinx.coroutines.launch

@Composable
fun AccountDrawerContent(
    drawerState: DrawerState,
    onMyEventsClick: () -> Unit,
    onFeedbackClicked: () -> Unit = {},
    onDeleteAccountClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var showConfirmationDialog by remember { mutableStateOf(false) }

    if (showConfirmationDialog){
        ConfirmLogoutDialog(
            onConfirm = {
                showConfirmationDialog = false
                onLogoutClicked()
            },
            onDismiss = { showConfirmationDialog = false }
        )
    }
    ModalDrawerSheet(
        modifier =
        Modifier.background(
            brush =
            Brush.linearGradient(
                colors =
                listOf(
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.colorScheme.surfaceDim,
                    MaterialTheme.colorScheme.surfaceBright
                )
            )
        )
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = { coroutineScope.launch { drawerState.close() } }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Drawer"
                )
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Account",
                style =
                MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_event_24),
                        contentDescription = null
                    )
                },
                label = {
                    Text("My Event Tickets")
                },
                selected = false,
                onClick = { onMyEventsClick() }
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_feedback_24),
                        contentDescription = null
                    )
                },
                label = {
                    Text("Feedback")
                },
                selected = false,
                onClick = {
                    onFeedbackClicked()
                }
            )

            // Settings Item
            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_settings_24),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        "Settings"
                    )
                },
                selected = false,
                onClick = onSettingsClicked
            )

            // Logout Item
            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_logout_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.9f)
                    )
                },
                label = {
                    Text(
                        "Log out",
                        color = MaterialTheme.colorScheme.error
                    )
                },
                selected = false,
                onClick = {
                   showConfirmationDialog = true
                },
                colors =
                NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                )
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_delete_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.9f)
                    )
                },
                label = {
                    Text(
                        "Delete account",
                        color = MaterialTheme.colorScheme.error
                    )
                },
                selected = false,
                onClick = {
                    onDeleteAccountClicked()
                },
                colors =
                NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                )
            )
        }
    }
}
