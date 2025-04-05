package com.newton.account.presentation.composables.account

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.R
import kotlinx.coroutines.*

@Composable
fun AccountDrawerContent(
    drawerState: DrawerState,
    onMyEventsClick: () -> Unit,
    onFeedbackClicked: () -> Unit = {},
    onDeleteAccountClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

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

            // My Events Item
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
                onClick = {}
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
                    onLogoutClicked()
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
