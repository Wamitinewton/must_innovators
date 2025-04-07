package com.newton.admin.presentation.club.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.newton.admin.presentation.club.event.ClubEvent
import com.newton.admin.presentation.club.view.composable.UpdateClubForm
import com.newton.admin.presentation.club.viewmodel.ClubViewModel
import com.newton.admin.presentation.events.view.composables.AdminSuccessScreen
import com.newton.commonUi.composables.DefaultScaffold
import com.newton.commonUi.ui.ErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClubScreen(
    viewmodel: ClubViewModel,
    onEvent: (ClubEvent) -> Unit
) {
    val clubState by viewmodel.clubState.collectAsState()

    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Club") },
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
