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
package com.newton.admin.presentation.notification.view.composables

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.notification.events.*
import com.newton.admin.presentation.notification.state.*
import com.newton.commonUi.ui.*
import java.time.format.*

@Composable
fun AddNewsLetterForm(newsState: NotificationState, onEvent: (NotificationEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Subject",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = newsState.subject,
                    onValueChange = { onEvent.invoke(NotificationEvent.SubjectChange(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter newsletter subject") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Message",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Row {
                        IconButton(
                            onClick = { onEvent.invoke(NotificationEvent.ShowLinkDialog(shown = true)) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Link,
                                contentDescription = "Add Link"
                            )
                        }

                        IconButton(
                            onClick = {
                                onEvent.invoke(NotificationEvent.MessageChange(message = newsState.message + "\n• "))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
                                contentDescription = "Add Bullet Point"
                            )
                        }

                        IconButton(
                            onClick = {
                                onEvent.invoke(NotificationEvent.MessageChange(message = newsState.message + "\n[IMAGE PLACEHOLDER]\n"))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = "Add Image"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                SelectionContainer {
                    BasicTextField(
                        value = newsState.message,
                        onValueChange = { onEvent.invoke(NotificationEvent.MessageChange(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Default
                        ),
                        keyboardActions = KeyboardActions(),
                        decorationBox = { innerTextField ->

                            Box {
                                if (newsState.message.isEmpty()) {
                                    Text(
                                        text = "Compose your newsletter message here. You can add links, bullet points, and image placeholders using the buttons above.",
                                        color = Color.Gray,
                                        fontSize = 16.sp
                                    )
                                }
                                innerTextField()
                            }
                        }

                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${newsState.message.length} characters",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 12.sp
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Will be sent to 1,245 subscribers"
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "Schedule for later"
                    )
                    Text(
                        text = "Send your newsletter at the optimal time",
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))


                Switch(
                    checked = newsState.isScheduled,
                    onCheckedChange = { onEvent.invoke(NotificationEvent.ScheduledChanged(it)) }
                )
            }
        }

        AnimatedVisibility(visible = newsState.isScheduled) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "Schedule Date and Time",
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = if (newsState.isScheduled && newsState.scheduledDateTime == null) MaterialTheme.colorScheme.error else Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable { onEvent.invoke(NotificationEvent.ShowDateDialog(true)) }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Select Date"
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = if (newsState.scheduledDateTime != null) {
                                    val formatter =
                                        DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a")
                                    newsState.scheduledDateTime.format(formatter)
                                } else {
                                    "Select date and time"
                                },
                                color = if (newsState.scheduledDateTime != null) Color.DarkGray else Color.Gray
                            )
                        }
                    }
                    if (newsState.isScheduled && newsState.scheduledDateTime == null) {
                        Text(
                            text = "Schedule date and time is required",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            onClick = {
                onEvent.invoke(NotificationEvent.SendNewsLetter)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Send Newsletter",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
