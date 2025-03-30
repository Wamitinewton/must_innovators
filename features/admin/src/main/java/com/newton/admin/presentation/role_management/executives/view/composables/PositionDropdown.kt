package com.newton.admin.presentation.role_management.executives.view.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.newton.admin.presentation.role_management.executives.events.ExecutiveEvents
import com.newton.admin.presentation.role_management.executives.states.ExecutiveState
import com.newton.core.enums.ExecutivePosition


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
                    },
                )
            }
        }
    }
}
