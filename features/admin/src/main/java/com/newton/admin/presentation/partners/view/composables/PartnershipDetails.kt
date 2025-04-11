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
package com.newton.admin.presentation.partners.view.composables

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.partners.events.*
import com.newton.admin.presentation.partners.states.*
import com.newton.commonUi.ui.*
import java.time.format.*

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
                    contentDescription = null
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
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null
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
                    }
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
                        contentDescription = null
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
                            contentDescription = null
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
