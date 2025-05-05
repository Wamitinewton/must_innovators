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

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.newton.admin.presentation.club.event.*
import com.newton.admin.presentation.club.view.composable.*
import com.newton.admin.presentation.club.viewmodel.*
import com.newton.admin.presentation.events.view.composables.*
import com.newton.commonUi.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClubScreen(
    viewmodel: ClubViewModel,
    onEvent: (ClubEvent) -> Unit
) {
    val clubState by viewmodel.clubState.collectAsState()

    CustomScaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Club") }
            )
        },
        isLoading = clubState.isLoading
    ) {
        when {
            clubState.isUpdatedSuccess -> {
                AdminSuccessScreen(
                    onFinish = {
                        onEvent.invoke(ClubEvent.ToDefault)
                        onEvent.invoke(ClubEvent.LoadClub)
                    },
                    text = "Club Updated Successfully"
                )
            }

            clubState.errorMessage != null -> {
                ErrorScreen(
                    message = clubState.errorMessage!!,
                    onRetry = { onEvent.invoke(ClubEvent.LoadClub) },
                    titleText = "Something wrong happened when loading Club data"
                )
            }

            else -> {
                UpdateClubForm(clubState, onEvent)
            }
        }
    }
}

data class SocialMediaLink(
    val platform: String,
    val url: String
)
