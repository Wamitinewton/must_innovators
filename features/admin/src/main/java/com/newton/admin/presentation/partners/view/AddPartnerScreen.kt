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
package com.newton.admin.presentation.partners.view

import androidx.activity.compose.*
import androidx.activity.result.*
import androidx.activity.result.contract.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import com.newton.admin.presentation.events.view.composables.*
import com.newton.admin.presentation.partners.events.*
import com.newton.admin.presentation.partners.states.*
import com.newton.admin.presentation.partners.view.composables.*
import com.newton.admin.presentation.partners.viewModel.*
import com.newton.commonUi.ui.*
import java.io.*
import java.time.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPartnerScreen(
    viewModel: PartnersViewModel,
    onEvent: (AddPartnersEvent) -> Unit
) {
    val context = LocalContext.current
    val partnersState by viewModel.addPartnersState.collectAsState()
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
                    onEvent.invoke(AddPartnersEvent.LogoChange(file))
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
                AddPartnersEffect.PickImage -> imageLauncher.launch(mediaRequest)
            }
        }
    }
    CustomScaffold(
        isLoading = partnersState.isLoading,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Partner",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Preview",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        when {
            partnersState.isSuccess -> {
                AdminSuccessScreen(
                    onFinish = {
                        onEvent.invoke(AddPartnersEvent.ToDefault)
                    },
                    text = "Partner added successfully"
                )
            }

            partnersState.errorMessage != null -> {
                ErrorScreen(
                    partnersState.errorMessage!!,
                    onRetry = { onEvent.invoke(AddPartnersEvent.AddPartners) }
                )
            }

            else -> {
                AddPartnerForm(
                    partnersState = partnersState,
                    onEvent = onEvent
                )
            }
        }
    }
    if (partnersState.showStartDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = partnersState.partnershipStartDate.toEpochDay() * 24 * 60 * 60 * 1000
        )

        DatePickerDialog(
            onDismissRequest = {
                onEvent.invoke(AddPartnersEvent.ShowStartDate(false))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { dateMillis ->
                            val date = Instant.ofEpochMilli(dateMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onEvent.invoke(AddPartnersEvent.StartDateChange(date))
                        }
                        onEvent.invoke(AddPartnersEvent.ShowStartDate(false))
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent.invoke(AddPartnersEvent.ShowStartDate(false))
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    if (partnersState.showEndDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = partnersState.partnershipEndDate?.toEpochDay()
                ?.times(24 * 60 * 60 * 1000)
                ?: (LocalDate.now().toEpochDay() * 24 * 60 * 60 * 1000)
        )
        DatePickerDialog(
            onDismissRequest = {
                onEvent.invoke(AddPartnersEvent.ShowEndDate(false))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { dateMillis ->
                            val date = Instant.ofEpochMilli(dateMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onEvent.invoke(AddPartnersEvent.EndDateChange(date))
                        }
                        onEvent.invoke(AddPartnersEvent.ShowEndDate(false))
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onEvent.invoke(AddPartnersEvent.ShowEndDate(false)) }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
