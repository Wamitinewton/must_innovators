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
package com.newton.admin.presentation.roleManagement.executives.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.roleManagement.executives.events.ExecutiveEvents
import com.newton.admin.presentation.roleManagement.executives.states.ExecutiveState

@Composable
fun AddExecutiveForm(execState: ExecutiveState, onEvent: (ExecutiveEvents) -> Unit) {
    val positions: List<String> = listOf(
        "Chair person",
        "Vice chair person",
        "coordinator",
        "Club Secretary",
        "Vice Secretary",
        "Treasurer",
        "Social media manager"
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = execState.bio,
                onValueChange = { onEvent.invoke(ExecutiveEvents.BioChanged(it)) },
                label = { Text("Bio") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null
                    )
                },
                minLines = 3,
                maxLines = 5,
                supportingText = {
                    execState.errors["bio"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }

        item {
            Text(
                text = "Lead position",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
            PositionDropdown(
                positions = positions,
                onEvent = onEvent,
                state = execState
            )
        }
        execState.selectedUser?.let { user ->
            item {
                SelectedUserCard(user = user)
                execState.errors["user"]?.let {
                    Text(
                        it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onEvent.invoke(ExecutiveEvents.AddExecutive)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = execState.bio.isNotEmpty() && execState.selectedUser != null && execState.position != null
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ADD EXECUTIVE",
                    fontSize = 16.sp
                )
            }
        }
    }
}
