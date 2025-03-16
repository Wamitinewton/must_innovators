package com.newton.admin.presentation.events.view.management

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.newton.admin.presentation.events.events.EventEvents
import com.newton.admin.presentation.events.viewmodel.EventsViewModel
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.models.admin_models.EventsFeedback
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyEvent(
    viewModel: EventsViewModel,
    onEvent: (EventEvents) -> Unit
) {
    val eventsState by viewModel.eventList.collectAsState()
    val event = eventsState.selectedEvent ?: EventsData(
        id = 1,
        name = "Annual Tech Conference",
        description = "Our biggest tech event of the year featuring industry speakers and networking opportunities.",
        date = "2025-07-18T16:30:00Z",
        location = "Convention Center",
        imageUrl = "",
        category = "Android",
        organizer = "Emmanuel",
        contactEmail = "bett@gmail.com",
        isVirtual = true,
    )

    var name by remember { mutableStateOf(event.name) }
    var category by remember { mutableStateOf(event.location) }
    var description by remember { mutableStateOf(event.description) }
    var imageUrl by remember { mutableStateOf("Emmanuel Bett") }
    var date by remember { mutableStateOf("2025-07-18T16:30:00Z") }
    var location by remember { mutableStateOf(event.location) }
    var organizer by remember { mutableStateOf("Emmanuel Bett") }
    var contactEmail by remember { mutableStateOf("emmanuel@gmail.com") }
    var isVirtual by remember { mutableStateOf(event.isVirtual) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details") },
                actions = {
                    if (eventsState.isEditing) {
                        IconButton(onClick = {
                            onEvent.invoke(EventEvents.EditingEvent(false))
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel")
                        }
                        IconButton(
                            onClick = {
                                val updatedEvent = event.copy(
//                                title = name,
//                                location = category,
//                                description = description,
//                                imageUrl = imageUrl,
////                                date = date,
//                                location = location,
//                                organizer = organizer,
//                                contactEmail = contactEmail,
//                                isVirtual = isVirtual
                                )
//                                onSaveClick(updatedEvent)
                            }
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Save")
                        }
                    } else {
                        IconButton(onClick = {
                            onEvent.invoke(EventEvents.EditingEvent(true))
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (eventsState.isEditing) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Image preview
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Form fields
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Event Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formatDateTimeForDisplay(date),
                    onValueChange = { /* Date picker would be implemented here */ },
                    label = { Text("Date and Time") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { /* Show date picker */ }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                        }
                    },
                    readOnly = true // Date would be set via picker dialog
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = organizer,
                    onValueChange = { organizer = it },
                    label = { Text("Organizer") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = contactEmail,
                    onValueChange = { contactEmail = it },
                    label = { Text("Contact Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Virtual Event")
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = isVirtual,
                        onCheckedChange = { isVirtual = it }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Event Image
                AsyncImage(
                    model = "https://mustinnovators.s3.amazonaws.com/event_images/AI.jpeg",
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Event Title and Category
                Column {
                    Text(
                        text = event.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = "Android",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 12.sp
                        )
                    }
                }

                HorizontalDivider()

                // Date and Location
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(formatDateTime("2025-07-18T16:30:00Z"))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (event.isVirtual) Icons.Default.Videocam else Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(event.location)
                }

                HorizontalDivider()

                // Description
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(event.description)

                // Organizer and Contact
                Text(
                    text = "Organizer Information",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Organizer",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
//                Text(event.organizer)
                    Text("Emmanuel Bett")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
//                Text(event.contactEmail)
                    Text("emmanuel@gmail.com")
                }

                // Virtual Badge if applicable
                if (event.isVirtual) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Videocam,
                                contentDescription = "Virtual Event",
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Virtual Event",
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        }
    }

}

private fun formatDateTime(dateString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateString)
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
    return "${zonedDateTime.format(dateFormatter)} at ${zonedDateTime.format(timeFormatter)}"
}

private fun formatDateTimeForDisplay(dateString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return zonedDateTime.format(formatter)
}