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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.partners.events.*
import com.newton.admin.presentation.partners.states.*
import com.newton.commonUi.ui.*

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
            modifier =
                Modifier
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
                        contentDescription = null
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
                        contentDescription = null
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
                        contentDescription = null
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
                        contentDescription = null
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
                        contentDescription = null
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
                        contentDescription = null
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
