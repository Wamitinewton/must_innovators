package com.newton.admin.presentation.community.view.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.community.events.UpdateCommunityEvent
import com.newton.admin.presentation.community.states.CommunityState
import com.newton.admin.presentation.community.states.UpdateCommunityState
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.toFormatedDate
import com.newton.core.data.mappers.toAdminSession
import com.newton.core.data.mappers.toAdminSessionList
import com.newton.core.domain.models.about_us.Community

@Composable
fun UpdateCommunityCard(
    communityData: Community,
    onEvent: (UpdateCommunityEvent) -> Unit,
    isEditing:Boolean,
    padding:PaddingValues,
    communityState: UpdateCommunityState
) {
    DefaultScaffold () {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            // Community header with name and recruitment status
            CommunityHeader(
                communityName = communityData.name,
                isRecruiting = communityData.isRecruiting,
                isEditing = isEditing,
                onNameChange = {
                    onEvent.invoke(UpdateCommunityEvent.NameChanged(it))
                },
                onRecruitingChange = {
                    onEvent.invoke(UpdateCommunityEvent.IsRecruitingChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Community description
            CommunitySection(
                title = "About",
                icon = Icons.Default.Description
            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = communityData.description,
                        onValueChange = {
                            onEvent.invoke(UpdateCommunityEvent.DescriptionChanged(it))

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

            // Leadership section
            CommunitySection(
                title = "Leadership",
                icon = Icons.Default.Group
            ) {
                LeadershipField(
                    label = "Community Lead",
                    value = communityData.communityLead.name,
                    isEditing = isEditing,
                    onValueChange = {
                        onEvent.invoke(UpdateCommunityEvent.LeadChanged(it))
                    }
                )

                LeadershipField(
                    label = "Co-Lead",
                    value = communityData.coLead.name,
                    isEditing = isEditing,
                    onValueChange = {
                        onEvent.invoke(UpdateCommunityEvent.CoLeadChanged(it))
                    }
                )

                LeadershipField(
                    label = "Secretary",
                    value = communityData.secretary.name,
                    isEditing = isEditing,
                    onValueChange = {
                        onEvent.invoke(UpdateCommunityEvent.SecretaryChanged(it)) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contact information
            CommunitySection(
                title = "Contact Information",
                icon = Icons.Default.Call
            ) {
                ContactField(
                    label = "Email",
                    value = communityData.email,
                    icon = Icons.Default.Email,
                    isEditing = isEditing,
                    keyboardType = KeyboardType.Email,
                    onValueChange = {
                        onEvent.invoke(UpdateCommunityEvent.EmailCHanged(it))}
                )

                ContactField(
                    label = "Phone",
                    value = communityData.phoneNumber,
                    icon = Icons.Default.Phone,
                    isEditing = isEditing,
                    keyboardType = KeyboardType.Phone,
                    onValueChange = {
                        onEvent.invoke(UpdateCommunityEvent.PhoneChanged(it))}
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sessions section - NEW
            CommunitySection(
                title = "Meeting Sessions",
                icon = Icons.Default.Schedule
            ) {
                if (communityData.sessions.isEmpty()) {
                    Text(
                        text = "No scheduled sessions",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                } else {
                    communityData.sessions.forEachIndexed { index, data ->
                        val session = data.toAdminSession()
                        SessionItem(
                            session = session,
                            isEditing = isEditing,
                            onEditClick = {
                                onEvent.invoke(UpdateCommunityEvent.SessionToEdit(session))
                                onEvent.invoke(UpdateCommunityEvent.ShowAddSession(true))
                            },
                            onDeleteClick = {
                                val newSessions = communityData.sessions.toAdminSessionList().toMutableList()
                                newSessions.removeAt(index)
                                onEvent.invoke(UpdateCommunityEvent.SessionsChanged(newSessions))
                            }
                        )

                        if (index < communityData.sessions.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tech stack
            CommunitySection(
                title = "Tech Stack",
                icon = Icons.Default.Settings
            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = communityData.techStack.joinToString(),
                        onValueChange = { onEvent.invoke(UpdateCommunityEvent.ToolsChanged(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Tech Stack") }
                    )
                } else {
                    Text(
                        text = communityData.techStack.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Community details
            CommunitySection(
                title = "Community Details",
                icon = Icons.Default.LocationOn
            ) {
                DetailRow(
                    label = "Founding Date",
                    value = communityData.foundingDate,
                    icon = Icons.Default.CalendarMonth,
                    isEditing = isEditing,
                    onValueChange = { onEvent.invoke(UpdateCommunityEvent.DateFoundedChanged(it)) }
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
                enter = fadeIn() + expandVertically(
                    animationSpec = spring(
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
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
                session = communityState.sessionToEdit,
                onDismiss = {
                    onEvent.invoke(UpdateCommunityEvent.ShowAddSession(false))
                },
                onSave = { session ->
                    val newSessions = communityState.sessions?.toMutableList()
                    if (communityState.sessionToEdit == null) {
                        newSessions!!.add(session)
                    } else {
                        val index = newSessions?.indexOf(communityState.sessionToEdit)
                        if (index != -1) {
                            newSessions?.set(index ?: 0, session)
                        }
                    }
                    newSessions?.let {
                        onEvent.invoke(UpdateCommunityEvent.SessionsChanged(it))
                    }
                    onEvent.invoke(UpdateCommunityEvent.ShowAddSocialDialog(false))
                    onEvent.invoke(UpdateCommunityEvent.SessionToEdit(null))
                }
            )
        }
    }

}