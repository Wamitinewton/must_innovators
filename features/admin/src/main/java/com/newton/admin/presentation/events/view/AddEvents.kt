package com.newton.admin.presentation.events.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.newton.admin.presentation.events.events.AddEventEvents
import com.newton.admin.presentation.events.states.AddEventEffect
import com.newton.admin.presentation.events.view.composables.ImageReceiptView
import com.newton.admin.presentation.events.view.composables.SelectCategoriesContentView
import com.newton.admin.presentation.events.view.composables.SelectImageButton
import com.newton.admin.presentation.events.viewmodel.AddEventViewModel
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import com.newton.common_ui.composables.dialog.CustomDatePickerDialog
import com.newton.common_ui.ui.ColumnWrapper
import com.newton.common_ui.ui.CustomClickableOutlinedTextField
import com.newton.common_ui.ui.toFormattedDate
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEvents(
    viewModel: AddEventViewModel,
    onEvent: (AddEventEvents)->Unit,
    navigator:NavController
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            val contentResolver = context.contentResolver
            val file = File(context.cacheDir, "temp_file_${System.currentTimeMillis()}")
            if (uri!= null){
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
                AddEventEffect.NavigateBack -> TODO()
                is AddEventEffect.ShowError -> TODO()
                is AddEventEffect.SaveImageFile -> TODO()
            }
        }
    }

    Scaffold(
        topBar = {

            MeruInnovatorsAppBar(
                title = "Add Event",

                )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Event Name
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it) },
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

            // Description
            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.onDescriptionChange(it) },
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
                value = state.date.toFormattedDate(),
                onClick = {
                    onEvent.invoke(AddEventEvents.Dialog(shown = true))
                },
                supportingText = {
                    state.errors["date"]?.let { Text(it) }
                }
            )



            Spacer(modifier = Modifier.height(16.dp))

            // Location
            OutlinedTextField(
                value = state.location,
                onValueChange = {
                    onEvent.invoke(AddEventEvents.ChangedLocation(it))
                },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Organizer
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

            // Contact Email
            OutlinedTextField(
                value = state.contactEmail,
                onValueChange = { viewModel.onContactEmailChange(it) },
                label = { Text("Contact Email*") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.errors["email"] != null,
                supportingText = { state.errors["email"]?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            OutlinedTextField(
                value = state.title,
                onValueChange = { viewModel.onTitleChange(it) },
                label = { Text("Title*") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.errors["title"] != null,
                supportingText = { state.errors["title"]?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Is Virtual Checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.isVirtual,
                    onCheckedChange = { viewModel.onIsVirtualChange(it) }
                )
                Text("Virtual Event")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    onEvent.invoke(AddEventEvents.AddEvent)
                    if(state.uploadSuccess){
                        navigator.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Event")
            }
        }

        // Category Selection Bottom Sheet
        if (state.showCategorySheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { onEvent.invoke(AddEventEvents.Sheet(shown = false)) }
            ) {
               ColumnWrapper(modifier = Modifier.heightIn(max = 640.dp)){
                   Text(
                        "Select Category",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
                    )

                   SelectCategoriesContentView(
                       onEvent = onEvent,
                       categories = viewModel.categories
                   )
                   Spacer(Modifier.size(24.dp))
               }

            }
        }
        if (state.isShowDialog) {
            CustomDatePickerDialog(
                initialValue = state.date,
                onDismiss = { viewModel.handleEvent(AddEventEvents.Dialog(shown = false)) },
                onConfirm = { viewModel.handleEvent(AddEventEvents.ChangeDate(it)) }
            )
        }

    }

}