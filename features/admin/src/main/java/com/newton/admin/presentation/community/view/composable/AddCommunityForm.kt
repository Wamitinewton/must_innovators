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
package com.newton.admin.presentation.community.view.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.community.events.*
import com.newton.admin.presentation.community.states.*
import com.newton.commonUi.ui.*

@Composable
fun AddCommunityForm(addCommunityState: CommunityState, onEvent: (CommunityEvent) -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Community Information",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                // Community Name
                OutlinedTextField(
                    value = addCommunityState.name,
                    onValueChange = {
                        onEvent.invoke(CommunityEvent.NameChanged(it))
                    },
                    label = { Text("Community Name") },
                    leadingIcon = { Icon(Icons.Default.Group, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    singleLine = true,
                    supportingText = {
                        addCommunityState.errors["name"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    }
                )
                OutlinedTextField(
                    value = addCommunityState.dateFounded,
                    onValueChange = {
                        onEvent.invoke(CommunityEvent.DateFoundedChanged(it))
                    },
                    label = { Text("Date Founded") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            onEvent.invoke(CommunityEvent.ShowDatePicker(true))
                        }) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = "Select Date"
                            )
                        }
                    },
                    readOnly = true,
                    supportingText = {
                        addCommunityState.errors["date"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    }
                )
                OutlinedTextField(
                    value = addCommunityState.description,
                    onValueChange = {
                        onEvent.invoke(CommunityEvent.DescriptionChanged(it))
                    },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    supportingText = {
                        addCommunityState.errors["description"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    }
                )
            }
        }
        CustomCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Leadership",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                LeadershipSelectField(
                    label = "Lead",
                    value = addCommunityState.lead,
                    onClick = {
                        onEvent.invoke(CommunityEvent.CurrentRoleSelectionChange("lead"))
                        onEvent.invoke(CommunityEvent.ShowBottomSheet(true))
                    }
                )
                addCommunityState.errors["lead"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                LeadershipSelectField(
                    label = "Co-Lead",
                    value = addCommunityState.coLead,
                    onClick = {
                        onEvent.invoke(CommunityEvent.CurrentRoleSelectionChange("co-lead"))
                        onEvent.invoke(CommunityEvent.ShowBottomSheet(true))
                    }
                )
                addCommunityState.errors["colead"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                LeadershipSelectField(
                    label = "Secretary",
                    value = addCommunityState.secretary,
                    onClick = {
                        onEvent.invoke(CommunityEvent.CurrentRoleSelectionChange("secretary"))
                        onEvent.invoke(CommunityEvent.ShowBottomSheet(true))
                    }
                )
                addCommunityState.errors["secretary"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        }
        // sessions
        CommunitySection(
            title = "Meeting Sessions",
            icon = Icons.Default.Schedule,
            trailing = {
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    onClick = {
                        onEvent.invoke(CommunityEvent.SessionToEdit(null))
                        onEvent.invoke(CommunityEvent.ShowAddSession(true))
                    }
                ) {
                    Row {
                        Icon(Icons.Default.Add, contentDescription = null)

                        Text("Add")
                    }
                }
            }
        ) {
            if (addCommunityState.sessions.isEmpty()) {
                Text(
                    text = "No scheduled sessions",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            } else {
                addCommunityState.sessions.forEachIndexed { index, session ->
                    SessionItem(
                        session = session,
                        isEditing = true,
                        onEditClick = {
                            onEvent.invoke(CommunityEvent.SessionToEdit(session))
                            onEvent.invoke(CommunityEvent.ShowAddSession(true))
                        },
                        onDeleteClick = {
                            val newSessions = addCommunityState.sessions.toMutableList()
                            newSessions.removeAt(index)
                            onEvent.invoke(CommunityEvent.SessionsChanged(newSessions))
                        }
                    )
                    if (index < addCommunityState.sessions.size - 1) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
            addCommunityState.errors["sessions"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }

        CommunitySection(
            title = "Contact Information",
            icon = Icons.Outlined.Contacts
        ) {
            OutlinedTextField(
                value = addCommunityState.email,
                onValueChange = {
                    onEvent.invoke(CommunityEvent.EmailCHanged(it))
                },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
            OutlinedTextField(
                value = addCommunityState.phone,
                onValueChange = {
                    onEvent.invoke(CommunityEvent.PhoneChanged(it))
                },
                label = { Text("Phone") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
        }

        CommunitySection(
            title = "Community Socials",
            icon = Icons.Outlined.Webhook,
            trailing = {
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    onClick = {
                        onEvent.invoke(CommunityEvent.SocialToEditChange(null))
                        onEvent.invoke(CommunityEvent.ShowAddSocialDialog(true))
                    }
                ) {
                    Row {
                        Icon(Icons.Default.Add, contentDescription = null)

                        Text("Add")
                    }
                }
            }
        ) {
            if (addCommunityState.socials.isEmpty()) {
                Text(
                    text = "There is No socials associated to this community",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            } else {
                addCommunityState.socials.forEachIndexed { index, social ->
                    SocialItem(
                        social = social,
                        onEditClick = {
                            onEvent.invoke(CommunityEvent.SocialToEditChange(social))
                            onEvent.invoke(CommunityEvent.ShowAddSocialDialog(true))
                        },
                        onDeleteClick = {
                            val newSocial = addCommunityState.socials.toMutableList()
                            newSocial.removeAt(index)
                            onEvent.invoke(CommunityEvent.SocialsChanged(newSocial))
                        }
                    )
                    if (index < addCommunityState.socials.size - 1) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
        CustomCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Tools & Technologies",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                // Tools used
                OutlinedTextField(
                    value = addCommunityState.toolsText,
                    onValueChange = {
                        onEvent.invoke(CommunityEvent.ToolsChanged(it))
                    },
                    label = { Text("Tools Used (comma separated)") },
                    leadingIcon = { Icon(Icons.Default.Build, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    supportingText = { Text("Example: Git, Docker, Jira") }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            onClick = {
                onEvent.invoke(CommunityEvent.AddCommunity)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Add Community")
        }
    }
}
