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
package com.newton.admin.presentation.events.view.management.composables.modify

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import com.newton.admin.presentation.events.events.*
import com.newton.commonUi.ui.*
import com.newton.network.domain.models.adminModels.*

@Composable
fun EditingEventForm(event: EventsData, onEvent: (EventUpdateEvent) -> Unit) {
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
            onValueChange = { EventUpdateEvent.OrganizerChanged(it) },
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
