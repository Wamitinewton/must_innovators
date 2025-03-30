package com.newton.admin.presentation.partners.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.community.view.composable.ErrorCard
import com.newton.admin.presentation.events.view.composables.CloseButton
import com.newton.admin.presentation.partners.events.AddPartnersEvent
import com.newton.admin.presentation.partners.states.AddPartnersEffect
import com.newton.admin.presentation.partners.viewModel.PartnersViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.OopsError
import com.newton.common_ui.ui.CustomButton
import com.newton.common_ui.ui.CustomDynamicAsyncImage
import com.newton.common_ui.ui.LoadingDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPartnerScreen(
    viewModel: PartnersViewModel,
    onEvent: (AddPartnersEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val partnersState by viewModel.addPartnersState.collectAsState()

    val partnerTypeOptions = listOf(
        "TECH", "ACADEMIC", "COMMUNITY", "MEDIA", "CORPORATE"
    )
    val statusOptions = listOf("ACTIVE", "INACTIVE", "PENDING", "COMPLETED")


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
                    OutlinedTextField(
                        value = partnersState.partnerName,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.NameChange(it)) },
                        label = { Text("Partner Name") },
                        placeholder = { Text("e.g. Google Cloud") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Business,
                                contentDescription = null,
                            )
                        },
                        singleLine = true,
                        supportingText = {
                            partnersState.errors["name"]?.let { Text(it) }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    ExposedDropdownMenuBox(
                        expanded = partnersState.partnerTypeExpanded,
                        onExpandedChange = {
                            onEvent.invoke(AddPartnersEvent.IsPartnerTypeExpanded(it))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = partnersState.partnerType,
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
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = partnersState.partnerTypeExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(), supportingText = {
                                partnersState.errors["partnerType"]?.let { Text(it) }
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = partnersState.partnerTypeExpanded,
                            onDismissRequest = {
                                onEvent.invoke(AddPartnersEvent.IsPartnerTypeExpanded(false))
                            }
                        ) {
                            partnerTypeOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        onEvent.invoke(AddPartnersEvent.TypeChange(option))
                                        onEvent.invoke(AddPartnersEvent.IsPartnerTypeExpanded(false))
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = partnersState.description,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.DescriptionChange(it)) },
                        label = { Text("Description") },
                        placeholder = { Text("Describe the partner and their contributions") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp),
                        minLines = 3,
                        supportingText = {
                            partnersState.errors["description"]?.let { Text(it) }
                        }
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
                    if (partnersState.partnershipLogo != null) {
                        val uri = partnersState.partnershipLogo
                        uri?.let { safeUri ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .padding(vertical = 10.dp)
                            ) {
                                CustomDynamicAsyncImage(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(MaterialTheme.shapes.small),
                                    imageUrl = safeUri,
                                    contentDescription = "Receipt Image",
                                    contentScale = ContentScale.Crop
                                )
                                CloseButton(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(12.dp),
                                    onDismiss = { onEvent.invoke(AddPartnersEvent.LogoChange(null)) }
                                )
                            }
                        }
                    } else {
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
                            onClick = {
                                onEvent.invoke(AddPartnersEvent.PickImage)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.FileUpload,
                                contentDescription = "Upload"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upload Logo")
                        }
                    }
                    partnersState.errors["logo"]?.let { Text(it) }
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
                    OutlinedTextField(
                        value = partnersState.website,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.WebsiteChange(it)) },
                        label = { Text("Website URL") },
                        placeholder = { Text("e.g. cloud.google.com") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                            )
                        },
                        supportingText = {
                            partnersState.errors["webUrl"]?.let { Text(it) }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = partnersState.contactEmail,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.ContactEmailChange(it)) },
                        label = { Text("Contact Email") },
                        placeholder = { Text("e.g. partnerships@google.com") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                            )
                        },
                        supportingText = {
                            partnersState.errors["email"]?.let { Text(it) }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = partnersState.contactPerson,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.ContactPersonChange(it)) },
                        label = { Text("Contact Person") },
                        placeholder = { Text("e.g. Alex Brown, Developer Advocate") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                            )
                        },
                        singleLine = true,
                        supportingText = {
                            partnersState.errors["person"]?.let { Text(it) }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Social Media",
                        fontWeight = FontWeight.Medium,
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = partnersState.socialLinkedIn,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.LinkedInChange(it)) },
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
                    OutlinedTextField(
                        value = partnersState.socialTwitter,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.TwitterChange(it)) },
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
                            onClick = { onEvent.invoke(AddPartnersEvent.ShowStartDate(true)) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = partnersState.partnershipStartDate.format(
                                    DateTimeFormatter.ofPattern(
                                        "MMM d, yyyy"
                                    )
                                ),
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
                            checked = partnersState.ongoingPartnership,
                            onCheckedChange = {
                                onEvent.invoke(AddPartnersEvent.OnGoingPartnership(it))
                                if (it) {
                                    onEvent.invoke(AddPartnersEvent.EndDateChange(null))
                                }
                            },
                        )
                    }
                    AnimatedVisibility(visible = !partnersState.ongoingPartnership) {
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
                                onClick = { onEvent.invoke(AddPartnersEvent.ShowEndDate(true)) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = partnersState.partnershipEndDate?.format(
                                        DateTimeFormatter.ofPattern("MMM d, yyyy")
                                    ) ?: "Select End Date",
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                    partnersState.errors["endDate"]?.let { Text(it) }
                    Spacer(modifier = Modifier.height(12.dp))

                    ExposedDropdownMenuBox(
                        expanded = partnersState.statusExpanded,
                        onExpandedChange = {
                            onEvent.invoke(AddPartnersEvent.IsStatusExpanded(it))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = partnersState.status,
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
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = partnersState.statusExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = partnersState.statusExpanded,
                            onDismissRequest = {
                                onEvent.invoke(AddPartnersEvent.IsStatusExpanded(false))
                            }
                        ) {
                            statusOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        onEvent.invoke(AddPartnersEvent.StatusChange(option))
                                        onEvent.invoke(AddPartnersEvent.IsStatusExpanded(false))
                                    }
                                )
                            }
                        }
                    }
                }
            }
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
                    OutlinedTextField(
                        value = partnersState.collaborationScope,
                        onValueChange = {
                            onEvent.invoke(AddPartnersEvent.ScopeChange(it))
                        },
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
                        maxLines = 3,
                        supportingText = {
                            partnersState.errors["scope"]?.let { Text(it) }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = partnersState.keyBenefits,
                        onValueChange = {
                            onEvent.invoke(AddPartnersEvent.BenefitsChange(it))
                        },
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
                        maxLines = 3,
                        supportingText = {
                            partnersState.errors["benefit"]?.let { Text(it) }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = partnersState.eventsSupported,
                        onValueChange = {
                            onEvent.invoke(AddPartnersEvent.SupportChange(it))
                        },
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
                    OutlinedTextField(
                        value = partnersState.resourcesProvided,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.ResourcesChange(it)) },
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
                        maxLines = 3,
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = partnersState.achievements,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.AchievementsChange(it)) },
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
                    OutlinedTextField(
                        value = partnersState.targetAudience,
                        onValueChange = { onEvent.invoke(AddPartnersEvent.AudienceTargetChange(it)) },
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
         OopsError(
             errorMessage = partnersState.errorMessage!!
         )
    }
}