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
package com.newton.admin.presentation.club.view

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.club.event.*
import com.newton.admin.presentation.club.viewmodel.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.admin.*
import timber.log.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClubScreen(
    viewmodel: ClubViewModel,
    onEvent: (ClubEvent) -> Unit
) {
    val clubState by viewmodel.clubState.collectAsState()

    var socialMediaLinks by remember {
        mutableStateOf(
            listOf(
                SocialMediaLink("", "")
            )
        )
    }

    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Club") }
            )
        },
        isLoading = clubState.isLoading
    ) {
        LazyColumn(
            modifier =
            Modifier
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
                        clubState.errors["name"]?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
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
                        clubState.errors["aboutus"]?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
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
                        clubState.errors["vision"]?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
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
                        clubState.errors["mission"]?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
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
                            socialMediaLinks =
                                socialMediaLinks.toMutableList().apply {
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
                            socialMediaLinks =
                                socialMediaLinks.toMutableList().apply {
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
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    onClick = {
                        Timber.d(".......................Update clicked...................")
                        val socials: List<Socials> =
                            socialMediaLinks.map {
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
}

data class SocialMediaLink(
    val platform: String,
    val url: String
)
