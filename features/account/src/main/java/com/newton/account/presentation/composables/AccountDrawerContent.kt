package com.newton.account.presentation.composables

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.newton.common_ui.R
import kotlinx.coroutines.launch

@Composable
fun AccountDrawerContent(
    drawerState: DrawerState,
    onMyEventsClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
            }) {
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
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            NavigationDrawerItem(
                icon = { Icon(
                    painter = painterResource(R.drawable.baseline_event_24),
                    contentDescription = null
                ) },
                label = { Text("My Event Tickets") },
                selected = false,
                onClick = {
                    onMyEventsClick()
                }
            )

            NavigationDrawerItem(
                icon = { Icon(
                    painter = painterResource(R.drawable.baseline_settings_24),
                    contentDescription = null
                ) },
                label = { Text("Settings") },
                selected = false,
                onClick = {}
            )

            NavigationDrawerItem(
                icon = { Icon(
                    painter = painterResource(R.drawable.baseline_logout_24),
                    contentDescription = null,
                    tint = Color.Red
                ) },
                label = { Text("Log out") },
                selected = false,
                onClick = {}
            )

            NavigationDrawerItem(
                icon = { Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = null,
                    tint = Color.Red
                ) },
                label = { Text("Delete account") },
                selected = false,
                onClick = {}
            )
        }
    }

}