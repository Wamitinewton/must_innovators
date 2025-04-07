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
package com.newton.admin.presentation.club.view.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.club.event.ClubEvent
import com.newton.admin.presentation.club.state.ClubState
import com.newton.admin.presentation.club.view.SocialMediaLink
import com.newton.commonUi.ui.CustomButton
import com.newton.network.domain.models.admin.Socials
import timber.log.Timber

@Composable
fun UpdateClubForm(clubState:ClubState, onEvent:(ClubEvent)->Unit) {
    var socialMediaLinks by remember {
        mutableStateOf(
            listOf(
                SocialMediaLink("", "")
            )
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            OutlinedTextField(
                value = clubState.name,
                onValueChange = { onEvent.invoke(ClubEvent.NameChanged(it)) },
                label = { Text("Club Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                supportingText = {
                    clubState.errors["name"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            )
        }
        item {
            OutlinedTextField(
                value = clubState.clubDetails,
                onValueChange = { onEvent.invoke(ClubEvent.ClubDetailChanged(it)) },
                label = { Text("About Us") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                supportingText = {
                    clubState.errors["aboutus"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            )
        }
        item {
            OutlinedTextField(
                value = clubState.vision,
                onValueChange = { onEvent.invoke(ClubEvent.VisionChanged(it)) },
                label = { Text("Vision") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                supportingText = {
                    clubState.errors["vision"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            )
        }
        item {
            OutlinedTextField(
                value = clubState.mission,
                onValueChange = { onEvent.invoke(ClubEvent.MissionChanged(it)) },
                label = { Text("Mission") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                supportingText = {
                    clubState.errors["mission"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Social Media Links",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(
                    onClick = {
                        socialMediaLinks = socialMediaLinks + SocialMediaLink("", "")
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Social Media Link")
                }
            }
        }
        itemsIndexed(socialMediaLinks) { index, link ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    value = link.platform,
                    onValueChange = {
                        socialMediaLinks = socialMediaLinks.toMutableList().apply {
                            this[index] = link.copy(platform = it)
                        }

                    },
                    label = { Text("Platform") },
                    modifier = Modifier.weight(.3f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = link.url,
                    onValueChange = {
                        socialMediaLinks = socialMediaLinks.toMutableList().apply {
                            this[index] = link.copy(url = it)
                        }
                    },
                    label = { Text("URL") },
                    modifier = Modifier.weight(.6f),
                    singleLine = true
                )
                AnimatedVisibility(
                    visible = socialMediaLinks.size > 1,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        onClick = {
                            socialMediaLinks =
                                socialMediaLinks.filterIndexed { i, _ -> i != index }
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove Link")
                    }
                }
            }
//                clubState.errors["socials"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }

        item {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = {
                    val socials: List<Socials> = socialMediaLinks.map {
                        Socials(it.platform, it.url)
                    }
                    onEvent.invoke(
                        ClubEvent.SocialsChanged(socials)
                    )
                    Timber.d("Socials:", socials)
                    onEvent.invoke(ClubEvent.UpdateClub)
                    Timber.d("Added")
                }
            ) {
                Text("Update Club")
            }
        }
    }
}