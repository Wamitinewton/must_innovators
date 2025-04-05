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

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.newton.admin.presentation.roleManagement.executives.events.*
import com.newton.admin.presentation.roleManagement.executives.states.*
import com.newton.core.enums.*

@Composable
fun PositionDropdown(
    positions: List<String>,
    onEvent: (ExecutiveEvents) -> Unit,
    state: ExecutiveState
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = state.position?.name ?: "Assign a position",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Group, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { onEvent.invoke(ExecutiveEvents.Expanded(true)) }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "position")
                }
            },
            readOnly = true,
            supportingText = {
                state.errors["position"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        )

        DropdownMenu(
            expanded = state.expanded,
            onDismissRequest = { onEvent.invoke(ExecutiveEvents.Expanded(false)) },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            positions.forEach { value ->
                fun getPosition(): ExecutivePosition {
                    return when (value) {
                        "Chair person" -> ExecutivePosition.CHAIRPERSON
                        "Vice chair person" -> ExecutivePosition.VICE_CHAIRPERSON
                        "coordinator" -> ExecutivePosition.COORDINATOR
                        "Club Secretary" -> ExecutivePosition.CLUB_SECRETARY
                        "Vice Secretary" -> ExecutivePosition.VICE_SECRETARY
                        "Treasurer" -> ExecutivePosition.TREASURER
                        "Social media manager" -> ExecutivePosition.SOCIAL_MEDIA_MANAGER
                        else -> throw IllegalArgumentException("Unknown position: $value")
                    }
                }

                DropdownMenuItem(
                    onClick = {
                        onEvent.invoke(ExecutiveEvents.PositionChanged(getPosition()))
                        onEvent.invoke(ExecutiveEvents.Expanded(false))
                    },
                    text = {
                        Text(text = value)
                    }
                )
            }
        }
    }
}
