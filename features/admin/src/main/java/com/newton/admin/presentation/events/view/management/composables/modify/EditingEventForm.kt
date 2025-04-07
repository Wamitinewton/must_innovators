package com.newton.admin.presentation.events.view.management.composables.modify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.newton.admin.presentation.events.events.EventUpdateEvent
import com.newton.commonUi.ui.CustomButton
import com.newton.common_ui.ui.toFormatedDate
import com.newton.network.domain.models.adminModels.EventsData

@Composable
fun EditingEventForm(event: EventsData, onEvent:(EventUpdateEvent)->Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Image preview
        AsyncImage(
            model = event.imageUrl,
            contentDescription = "Event Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        // Form fields
        OutlinedTextField(
            value = event.name,
            onValueChange = { onEvent.invoke(EventUpdateEvent.NameChanged(it)) },
            label = { Text("Event Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = event.category,
            onValueChange = { onEvent.invoke(EventUpdateEvent.CategoryChanged(it)) },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = event.description,
            onValueChange = { onEvent.invoke(EventUpdateEvent.DescriptionChanged(it)) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

//                        OutlinedTextField(
//                            value = imageUrl,
//                            onValueChange = { imageUrl = it },
//                            label = { Text("Image URL") },
//                            modifier = Modifier.fillMaxWidth()
//                        )

        OutlinedTextField(
            value = event.date.toFormatedDate(),
            onValueChange = { /* Date picker would be implemented here */ },
            label = { Text("Date and Time") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { /* Show date picker */ }) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Select Date"
                    )
                }
            },
            readOnly = true
        )

        OutlinedTextField(
            value = event.location,
            onValueChange = { onEvent.invoke(EventUpdateEvent.LocationChanged(it)) },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = event.organizer,
            onValueChange = { EventUpdateEvent.OrganizerChanged(it)  },
            label = { Text("Organizer") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = event.contactEmail,
            onValueChange = { EventUpdateEvent.ContactEmailChanged(it) },
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
                checked = event.isVirtual,
                onCheckedChange = { EventUpdateEvent.VirtualChanged(it) }
            )
        }
        Spacer(Modifier.height(8.dp))
        CustomButton(
            onClick = {
                onEvent.invoke(EventUpdateEvent.Update(event.id))
            }
        ) {
            Icon(Icons.Default.Save, contentDescription = "save")

            Text("Update Event", style = MaterialTheme.typography.headlineMedium)
        }
    }
}