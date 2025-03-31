package com.newton.admin.presentation.partners.view.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.partners.events.AddPartnersEvent
import com.newton.admin.presentation.partners.states.AddPartnersState
import com.newton.common_ui.ui.CustomCard
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnershipDetails(
    partnersState: AddPartnersState,
    onEvent: (AddPartnersEvent) -> Unit
) {
    val statusOptions = listOf("ACTIVE", "INACTIVE", "PENDING", "COMPLETED")
    CustomCard(
        modifier = Modifier.fillMaxWidth(),
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
            partnersState.errors["endDate"]?.let {
                Text(
                    it,
                    color = MaterialTheme.colorScheme.error
                )
            }
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
}