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
package com.newton.admin.presentation.events.view.composables

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.events.AddEventEvents
import com.newton.admin.presentation.events.states.AddEventEffect
import com.newton.admin.presentation.events.states.AddEventState
import com.newton.admin.presentation.events.viewmodel.AddEventViewModel
import com.newton.commonUi.ui.CustomClickableOutlinedTextField
import com.newton.commonUi.ui.toFormattedDate
import java.io.File
import java.io.FileOutputStream

@Composable
fun AddEventContent(state: AddEventState, onEvent: (AddEventEvents) -> Unit, viewModel: AddEventViewModel) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            val contentResolver = context.contentResolver
            val file = File(context.cacheDir, "temp_file_${System.currentTimeMillis()}")
            if (uri != null) {
                try {
                    contentResolver.openInputStream(uri)?.use { inputStream ->
                        FileOutputStream(file).use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    onEvent.invoke(AddEventEvents.ChangedFile(file))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    )
    val mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
    val mediaRequest = PickVisualMediaRequest(mediaType)
    LaunchedEffect(Unit) {
        viewModel.uiSideEffect.collect { effect ->
            when (effect) {
                AddEventEffect.PickImage -> imageLauncher.launch(mediaRequest)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = { onEvent.invoke(AddEventEvents.ChangedName(it)) },
            label = { Text("Event Name*") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.errors["name"] != null,
            supportingText = { state.errors["name"]?.let { Text(it) } }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomClickableOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = "Category",
            trailingIcon = Icons.Outlined.Category,
            value = state.category,
            onClick = {
                onEvent.invoke(AddEventEvents.Sheet(shown = true))
            },
            supportingText = {
                state.errors["category"]?.let { Text(it) }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.description,
            onValueChange = {
                onEvent.invoke(AddEventEvents.ChangedDescription(it))
            },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        Spacer(modifier = Modifier.height(16.dp))
        SelectImageButton(
            onEvent = onEvent
        )
        ImageReceiptView(state, onEvent)

        Spacer(modifier = Modifier.height(16.dp))

        CustomClickableOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = "Event Date",
            trailingIcon = Icons.Default.Today,
            value = state.scheduledDateTime.toFormattedDate(),
            onClick = {
                onEvent.invoke(AddEventEvents.ShowDateDialog(shown = true))
            },
            supportingText = {
                state.errors["date"]?.let { Text(it) }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.location,
            onValueChange = {
                onEvent.invoke(AddEventEvents.ChangedLocation(it))
            },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.organizer,
            onValueChange = {
                onEvent.invoke(AddEventEvents.ChangedOrganizer(it))
            },
            label = { Text("Organizer*") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.errors["organizer"] != null,
            supportingText = { state.errors["organizer"]?.let { Text(it) } }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.contactEmail,
            onValueChange = {
                onEvent.invoke(AddEventEvents.ChangedContactEmail(it))
            },
            label = { Text("Contact Email*") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.errors["email"] != null,
            supportingText = { state.errors["email"]?.let { Text(it) } }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.title,
            onValueChange = {
                onEvent.invoke(AddEventEvents.ChangedTitle(it))
            },
            label = { Text("Title*") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.errors["title"] != null,
            supportingText = { state.errors["title"]?.let { Text(it) } }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = state.isVirtual,
                onCheckedChange = {
                    onEvent.invoke(AddEventEvents.ChangedVirtual(it))
                }
            )
            Text("Virtual Event")
        }
        AnimatedVisibility(
            visible = state.isVirtual
        ) {
            OutlinedTextField(
                value = state.meetingLink,
                onValueChange = {
                    onEvent.invoke(AddEventEvents.ChangedMeetingLink(it))
                },
                label = { Text("Meeting link*") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.errors["meetingLink"] != null,
                supportingText = { state.errors["meetingLink"]?.let { Text(it) } }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                onEvent.invoke(AddEventEvents.AddEvent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Event")
        }
    }
}
