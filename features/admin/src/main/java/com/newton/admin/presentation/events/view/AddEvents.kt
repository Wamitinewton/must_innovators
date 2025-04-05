package com.newton.admin.presentation.events.view

import androidx.activity.compose.*
import androidx.activity.result.*
import androidx.activity.result.contract.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.states.*
import com.newton.admin.presentation.events.view.composables.*
import com.newton.admin.presentation.events.viewmodel.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import java.io.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEvents(
    viewModel: AddEventViewModel,
    onEvent: (AddEventEvents) -> Unit,
    navigator: NavController
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val imageLauncher =
        rememberLauncherForActivityResult(
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
                AddEventEffect.NavigateBack -> TODO()
                is AddEventEffect.ShowError -> TODO()
                is AddEventEffect.SaveImageFile -> TODO()
            }
        }
    }

    Scaffold(
        topBar = {
            MeruInnovatorsAppBar(
                title = "Add Event"
            )
        }
    ) { paddingValues ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Event Name
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

            // Description
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
                onValueChange = {
                    onEvent.invoke(AddEventEvents.ChangedContactEmail(it))
                },
                label = { Text("Contact Email*") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.errors["email"] != null,
                supportingText = { state.errors["email"]?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
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

            // Is Virtual Checkbox
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

            if (state.isVirtual) {
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

            // Submit Button
            Button(
                onClick = {
                    onEvent.invoke(AddEventEvents.AddEvent)
                    if (state.uploadSuccess) {
                        navigator.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Event")
            }
        }
        if (state.isLoading) {
            LoadingDialog()
        }
        if (state.uploadSuccess) {
            onEvent.invoke(AddEventEvents.ToDefaultSate)
        }
        // Category Selection Bottom Sheet
        if (state.showCategorySheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { onEvent.invoke(AddEventEvents.Sheet(shown = false)) }
            ) {
                ColumnWrapper(modifier = Modifier.heightIn(max = 640.dp)) {
                    Text(
                        "Select Category",
                        style = MaterialTheme.typography.titleLarge,
                        modifier =
                        Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
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
                onConfirm = {
                    viewModel.handleEvent(AddEventEvents.ChangeDate(it))
                    viewModel.handleEvent(AddEventEvents.Dialog(shown = false))
                }
            )
        }
    }
}
