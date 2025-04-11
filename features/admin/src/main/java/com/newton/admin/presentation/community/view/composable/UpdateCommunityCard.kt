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

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.community.events.*
import com.newton.admin.presentation.community.states.*
import com.newton.commonUi.ui.FlowRow
import com.newton.network.data.mappers.*
import com.newton.network.domain.models.aboutUs.*

@Composable
fun UpdateCommunityCard(
    communityData: Community,
    onEvent: (UpdateCommunityEvent) -> Unit,
    isEditing: Boolean,
    communityState: UpdateCommunityState
) {
    var name by remember { mutableStateOf(communityData.name) }
    var description by remember { mutableStateOf(communityData.description) }
    var lead by remember { mutableStateOf(communityData.communityLead.username) }
    var coLead by remember { mutableStateOf(communityData.coLead.username) }
    var secretary by remember { mutableStateOf(communityData.secretary.username) }
    var email by remember { mutableStateOf(communityData.email) }
    var sessions by remember { mutableStateOf(communityData.sessions) }
    var phone by remember { mutableStateOf(communityData.phoneNumber) }
    var tools by remember { mutableStateOf(communityData.techStack) }
    var foundedOn by remember { mutableStateOf(communityData.foundingDate) }
    var recruiting by remember { mutableStateOf(communityData.isRecruiting) }
    var sessionToEdit by remember { mutableStateOf<Session?>(null) }
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CommunityHeader(
            communityName = name,
            isRecruiting = recruiting,
            isEditing = isEditing,
            onNameChange = {
                name = it
            },
            onRecruitingChange = {
                recruiting = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
        CommunitySection(
            title = "About",
            icon = Icons.Default.Description
        ) {
            if (isEditing) {
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Description") },
                    maxLines = 5
                )
            } else {
                Text(
                    text = communityData.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        CommunitySection(
            title = "Leadership",
            icon = Icons.Default.Group
        ) {
            LeadershipField(
                label = "Community Lead",
                value = lead,
                isEditing = isEditing,
                onValueChange = {
                    lead = it
                }
            )

            LeadershipField(
                label = "Co-Lead",
                value = coLead,
                isEditing = isEditing,
                onValueChange = {
                    coLead = it
                }
            )

            LeadershipField(
                label = "Secretary",
                value = secretary,
                isEditing = isEditing,
                onValueChange = {
                    secretary = it
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        CommunitySection(
            title = "Contact Information",
            icon = Icons.Default.Call
        ) {
            ContactField(
                label = "Email",
                value = email,
                icon = Icons.Default.Email,
                isEditing = isEditing,
                keyboardType = KeyboardType.Email,
                onValueChange = {
                    email = it
                }
            )

            ContactField(
                label = "Phone",
                value = phone,
                icon = Icons.Default.Phone,
                isEditing = isEditing,
                keyboardType = KeyboardType.Phone,
                onValueChange = {
                    phone = it
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        CommunitySection(
            title = "Meeting Sessions",
            icon = Icons.Default.Schedule
        ) {
            if (sessions.isEmpty()) {
                Text(
                    text = "No scheduled sessions",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier =
                    Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            } else {
                sessions.forEachIndexed { index, data ->
                    SessionItem(
                        session = data.toAdminSession(),
                        isEditing = isEditing,
                        onEditClick = {
                            sessionToEdit = data
                            onEvent.invoke(UpdateCommunityEvent.ShowAddSession(true))
                        },
                        onDeleteClick = {
                            val newSessions = sessions.toMutableList()
                            newSessions.removeAt(index)
                            sessions = newSessions
                        }
                    )

                    if (index < communityData.sessions.size - 1) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        CommunitySection(
            title = "Tech Stack",
            icon = Icons.Default.Settings
        ) {
            if (isEditing) {
                OutlinedTextField(
                    value = tools.joinToString(),
                    onValueChange = { tool ->
                        tools = tool.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Tech Stack") }
                )
            } else {
                FlowRow {
                    tools.forEach { tool ->
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier.padding(horizontal = 3.dp)
                        ) {
                            Text(
                                text = tool,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        CommunitySection(
            title = "Community Details",
            icon = Icons.Default.LocationOn
        ) {
            DetailRow(
                label = "Founding Date",
                value = foundedOn,
                icon = Icons.Default.CalendarMonth,
                isEditing = isEditing,
                onValueChange = { foundedOn = it }
            )

//                    DetailRow(
//                        label = "Total Members",
//                        value = communityData.total_members.toString(),
//                        icon = Icons.Default.Person,
//                        isEditing = isEditing,
//                        keyboardType = KeyboardType.Number,
//                        onValueChange = {
//                            val newValue = it.toIntOrNull() ?: 0
//                            communityData = communityData.copy(total_members = newValue)
//                        }
//                    )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Social links
//                CommunitySection(
//                    title = "Social Media",
//                    icon = Icons.Default.LocationOn
//                ) {
//                    SocialField(
//                        label = "GitHub",
//                        value = communityData.socials.github,
//                        isEditing = isEditing,
//                        onValueChange = {
//                            communityData = communityData.copy(
//                                socials = communityData.socials.copy(github = it)
//                            )
//                        }
//                    )
//
//                    SocialField(
//                        label = "Twitter",
//                        value = communityData.socials.twitter,
//                        isEditing = isEditing,
//                        onValueChange = {
//                            communityData = communityData.copy(
//                                socials = communityData.socials.copy(twitter = it)
//                            )
//                        }
//                    )
//
//                    SocialField(
//                        label = "LinkedIn",
//                        value = communityData.socials.linkedin,
//                        isEditing = isEditing,
//                        onValueChange = {
//                            communityData = communityData.copy(
//                                socials = communityData.socials.copy(linkedin = it)
//                            )
//                        }
//                    )
//
//                    SocialField(
//                        label = "Discord",
//                        value = communityData.socials.discord,
//                        isEditing = isEditing,
//                        onValueChange = {
//                            communityData = communityData.copy(
//                                socials = communityData.socials.copy(discord = it)
//                            )
//                        }
//                    )
//                }

        Spacer(modifier = Modifier.height(32.dp))

        // Save button (visible only in edit mode)
        AnimatedVisibility(
            visible = isEditing,
            enter =
            fadeIn() +
                expandVertically(
                    animationSpec =
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ),
            exit = fadeOut() + shrinkVertically()
        ) {
            Button(
                onClick = {
                    onEvent.invoke(UpdateCommunityEvent.UpdateCommunity(communityData.id))
                    onEvent.invoke(UpdateCommunityEvent.IsEditingChange(false))
                },
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Save Changes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    if (communityState.showAddSessionDialog) {
        SessionDialog(
            session = sessionToEdit?.toAdminSession(),
            onDismiss = {
                onEvent.invoke(UpdateCommunityEvent.ShowAddSession(false))
            },
            onSave = { session ->
                val newSessions = sessions.toMutableList()
                if (sessionToEdit == null) {
                    newSessions.add(session.toAboutUs())
                } else {
                    val index = newSessions.indexOf(sessionToEdit)
                    if (index != -1) {
                        newSessions[index] = session.toAboutUs()
                    }
                }
                newSessions.let {
                    sessions = it
                }
                onEvent.invoke(UpdateCommunityEvent.ShowAddSocialDialog(false))
                sessionToEdit = null
            }
        )
    }
}
