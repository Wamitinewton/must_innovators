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
package com.newton.admin.presentation.events.view.management

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.view.composables.*
import com.newton.admin.presentation.events.view.management.composables.modify.*
import com.newton.admin.presentation.events.viewmodel.*
import com.newton.commonUi.ui.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyEvent(
    viewModel: AdminEventsSharedViewModel,
    onEvent: (EventUpdateEvent) -> Unit,
    updateEventsViewModel: UpdateEventsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val state by updateEventsViewModel.updateState.collectAsState()


    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearSelectedEvent()
        }
    }

    var isEditing by remember { mutableStateOf(false) }
    CustomScaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details") },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            isEditing = false
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel")
                        }
                    } else {
                        IconButton(onClick = {
                            isEditing = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        },
        isLoading = state.isLoading
    ) {
        when (uiState) {
            is UpdateEvent.Error -> {
                OopsError(errorMessage = (uiState as UpdateEvent.Error).message)
            }

            UpdateEvent.Initial -> {
                CircularProgressIndicator()
            }

            is UpdateEvent.Success -> {
                val event = (uiState as UpdateEvent.Success).event
                if (isEditing) {
                    EditingEventForm(event, onEvent)
                } else {
                    EventDetailView(event)
                }
            }
        }
        when {
            state.errorMessage != null -> {
                OopsError(
                    errorMessage = state.errorMessage!!
                )
            }

            state.uploadSuccess -> {
                AdminSuccessScreen(
                    onFinish = {
                        onEvent.invoke(EventUpdateEvent.ToDefault)
                    },
                    text = "Event Updated successfully"
                )
            }
        }
    }
}
