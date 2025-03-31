package com.newton.admin.presentation.partners.view.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.partners.events.AddPartnersEvent
import com.newton.admin.presentation.partners.states.AddPartnersState
import com.newton.common_ui.ui.CustomCard

@Composable
fun CollaborationDetails(
    modifier: Modifier = Modifier,
    partnersState: AddPartnersState,
    onEvent: (AddPartnersEvent) -> Unit
) {
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
                    partnersState.errors["scope"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
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
                    partnersState.errors["benefit"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
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
                maxLines = 3,
                supportingText = {
                    partnersState.errors["supported"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
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
                supportingText = {
                    partnersState.errors["resources"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
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
                maxLines = 3,
                supportingText = {
                    partnersState.errors["achievements"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
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
                maxLines = 2,
                supportingText = {
                    partnersState.errors["targetAudience"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    }
}