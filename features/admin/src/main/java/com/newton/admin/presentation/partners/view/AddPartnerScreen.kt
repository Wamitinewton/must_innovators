package com.newton.admin.presentation.partners.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.partners.events.AddPartnersEvent
import com.newton.admin.presentation.partners.states.AddPartnersEffect
import com.newton.admin.presentation.partners.view.composables.BasicInfo
import com.newton.admin.presentation.partners.view.composables.CollaborationDetails
import com.newton.admin.presentation.partners.view.composables.ContactInfo
import com.newton.admin.presentation.partners.view.composables.LogoCard
import com.newton.admin.presentation.partners.view.composables.PartnershipDetails
import com.newton.admin.presentation.partners.viewModel.PartnersViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.OopsError
import com.newton.common_ui.ui.CustomButton
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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
    DefaultScaffold(
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
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
           BasicInfo(
               partnersState = partnersState,
               onEvent = onEvent
           )
            LogoCard(
                partnersState = partnersState,
                onEvent = onEvent
            )
            ContactInfo(
                partnersState = partnersState,
                onEvent = onEvent
            )
            PartnershipDetails(
                partnersState = partnersState,
                onEvent = onEvent
            )
            CollaborationDetails(
                partnersState = partnersState,
                onEvent = onEvent
            )
            CustomButton(
                onClick = {
                    onEvent.invoke(AddPartnersEvent.AddPartners)
                    Timber.d("Adding partner")
                    Timber.d("errors: "+ partnersState.errors)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Save Partner",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
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
     if (partnersState.errorMessage != null){
         DefaultScaffold {
             OopsError(
                 errorMessage = partnersState.errorMessage!!
             )
         }
    }
}