package com.newton.admin.presentation.partners.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.common_ui.ui.CustomButton
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPartnerScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Partner data states
    var partnerName by remember { mutableStateOf("") }
    var partnerType by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }
    var contactPerson by remember { mutableStateOf("") }
    var socialLinkedIn by remember { mutableStateOf("") }
    var socialTwitter by remember { mutableStateOf("") }
    var partnershipStartDate by remember { mutableStateOf(LocalDate.now()) }
    var ongoingPartnership by remember { mutableStateOf(true) }
    var partnershipEndDate by remember { mutableStateOf<LocalDate?>(null) }
    var collaborationScope by remember { mutableStateOf("") }
    var keyBenefits by remember { mutableStateOf("") }
    var eventsSupported by remember { mutableStateOf("") }
    var resourcesProvided by remember { mutableStateOf("") }
    var achievements by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Active") }
    var targetAudience by remember { mutableStateOf("") }

    // Dropdown expanded states
    var partnerTypeExpanded by remember { mutableStateOf(false) }
    var statusExpanded by remember { mutableStateOf(false) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    // Partner types and status options
    val partnerTypeOptions = listOf("Tech Partner", "Corporate Partner", "Academic Partner", "Community Partner", "Media Partner")
    val statusOptions = listOf("Active", "Inactive", "Pending", "Completed")


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Partner",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { /* Preview functionality */ }) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Preview",
                            tint = Color.White
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Basic Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Basic Information",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Partner Name
                    OutlinedTextField(
                        value = partnerName,
                        onValueChange = { partnerName = it },
                        label = { Text("Partner Name") },
                        placeholder = { Text("e.g. Google Cloud") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Business,
                                contentDescription = null,
                            )
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Partner Type Dropdown
                    ExposedDropdownMenuBox(
                        expanded = partnerTypeExpanded,
                        onExpandedChange = { partnerTypeExpanded = it },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = partnerType,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Partner Type") },
                            placeholder = { Text("Select partner type") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Category,
                                    contentDescription = null,
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = partnerTypeExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = partnerTypeExpanded,
                            onDismissRequest = { partnerTypeExpanded = false }
                        ) {
                            partnerTypeOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        partnerType = option
                                        partnerTypeExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Description
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        placeholder = { Text("Describe the partner and their contributions") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp),
                        minLines = 3,
                    )
                }
            }

            // Logo Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Partner Logo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logo Placeholder
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray.copy(alpha = 0.3f))
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = "Add Logo",
                            modifier = Modifier.size(40.dp),
                            tint = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomButton(
                        onClick = { /* Handle logo upload */ },
                    ) {
                        Icon(
                            imageVector = Icons.Default.FileUpload,
                            contentDescription = "Upload"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Upload Logo")
                    }
                }
            }

            // Contact Information Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Contact Information",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Website URL
                    OutlinedTextField(
                        value = website,
                        onValueChange = { website = it },
                        label = { Text("Website URL") },
                        placeholder = { Text("e.g. cloud.google.com") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Contact Email
                    OutlinedTextField(
                        value = contactEmail,
                        onValueChange = { contactEmail = it },
                        label = { Text("Contact Email") },
                        placeholder = { Text("e.g. partnerships@google.com") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Contact Person
                    OutlinedTextField(
                        value = contactPerson,
                        onValueChange = { contactPerson = it },
                        label = { Text("Contact Person") },
                        placeholder = { Text("e.g. Alex Brown, Developer Advocate") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                            )
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Social Media",
                        fontWeight = FontWeight.Medium,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // LinkedIn
                    OutlinedTextField(
                        value = socialLinkedIn,
                        onValueChange = { socialLinkedIn = it },
                        label = { Text("LinkedIn") },
                        placeholder = { Text("LinkedIn profile or page URL") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF0077B5)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("in", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Twitter
                    OutlinedTextField(
                        value = socialTwitter,
                        onValueChange = { socialTwitter = it },
                        label = { Text("Twitter/X") },
                        placeholder = { Text("Twitter/X profile URL") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1DA1F2)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("X", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        },
                        singleLine = true
                    )
                }
            }

            // Partnership Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Partnership Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Start Date
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Start Date: ",
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        OutlinedButton(
                            onClick = { showStartDatePicker = true },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = partnershipStartDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy")),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Ongoing Partnership Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Ongoing Partnership",
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Switch(
                            checked = ongoingPartnership,
                            onCheckedChange = {
                                ongoingPartnership = it
                                if (it) {
                                    partnershipEndDate = null
                                }
                            },
                        )
                    }

                    // End Date (visible only if not ongoing)
                    AnimatedVisibility(visible = !ongoingPartnership) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.EventBusy,
                                contentDescription = null,
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "End Date: ",
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            OutlinedButton(
                                onClick = { showEndDatePicker = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = partnershipEndDate?.format(DateTimeFormatter.ofPattern("MMM d, yyyy")) ?: "Select End Date",
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Status Dropdown
                    ExposedDropdownMenuBox(
                        expanded = statusExpanded,
                        onExpandedChange = { statusExpanded = it },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = status,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Partnership Status") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.SignalCellularAlt,
                                    contentDescription = null,
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = statusExpanded,
                            onDismissRequest = { statusExpanded = false }
                        ) {
                            statusOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        status = option
                                        statusExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Collaboration Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Collaboration Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Collaboration Scope
                    OutlinedTextField(
                        value = collaborationScope,
                        onValueChange = { collaborationScope = it },
                        label = { Text("Collaboration Scope") },
                        placeholder = { Text("e.g. Cloud training, credits for projects, mentorship") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Assignment,
                                contentDescription = null,
                            )
                        },
                        minLines = 2,
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Key Benefits
                    OutlinedTextField(
                        value = keyBenefits,
                        onValueChange = { keyBenefits = it },
                        label = { Text("Key Benefits") },
                        placeholder = { Text("e.g. Free cloud credits, certification scholarships") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = null,
                            )
                        },
                        minLines = 2,
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Events Supported
                    OutlinedTextField(
                        value = eventsSupported,
                        onValueChange = { eventsSupported = it },
                        label = { Text("Events Supported") },
                        placeholder = { Text("e.g. Annual Solution Challenge, Cloud Study Jams") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Event,
                                contentDescription = null,
                            )
                        },
                        minLines = 2,
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Resources Provided
                    OutlinedTextField(
                        value = resourcesProvided,
                        onValueChange = { resourcesProvided = it },
                        label = { Text("Resources Provided") },
                        placeholder = { Text("e.g. Qwiklabs licenses, tutorials") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Inventory,
                                contentDescription = null,
                            )
                        },
                        minLines = 2,
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Achievements
                    OutlinedTextField(
                        value = achievements,
                        onValueChange = { achievements = it },
                        label = { Text("Achievements") },
                        placeholder = { Text("e.g. 200+ members certified in 2023") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                            )
                        },
                        minLines = 2,
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Target Audience
                    OutlinedTextField(
                        value = targetAudience,
                        onValueChange = { targetAudience = it },
                        label = { Text("Target Audience") },
                        placeholder = { Text("e.g. Computer Science and Engineering students") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Groups,
                                contentDescription = null,
                            )
                        },
                        minLines = 1,
                        maxLines = 2
                    )
                }
            }

            // Save Button
            CustomButton(
                onClick = {
                    // Validate and save partnership
                    if (partnerName.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Partner name is required")
                        }
                    } else if (partnerType.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Partner type is required")
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Partner added successfully!")
                        }
                    }
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
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Extra space at bottom for better scrolling
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Start Date Picker Dialog
    if (showStartDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = partnershipStartDate.toEpochDay() * 24 * 60 * 60 * 1000
        )

        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { dateMillis ->
                            val date = java.time.Instant.ofEpochMilli(dateMillis)
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate()
                            partnershipStartDate = date
                        }
                        showStartDatePicker = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showStartDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // End Date Picker Dialog
    if (showEndDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = partnershipEndDate?.toEpochDay()?.times(24 * 60 * 60 * 1000)
                ?: (LocalDate.now().toEpochDay() * 24 * 60 * 60 * 1000)
        )

        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { dateMillis ->
                            val date = java.time.Instant.ofEpochMilli(dateMillis)
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate()
                            partnershipEndDate = date
                        }
                        showEndDatePicker = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEndDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}