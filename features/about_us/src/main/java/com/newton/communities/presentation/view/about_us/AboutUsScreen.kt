package com.newton.communities.presentation.view.about_us

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.communities.presentation.events.CommunityUiEvent
import com.newton.communities.presentation.view.about_us.composables.SectionHeading
import com.newton.communities.presentation.view_model.ClubBioViewModel
import com.newton.communities.presentation.view_model.CommunitiesViewModel
import com.newton.communities.presentation.view_model.ExecutiveViewModel
import com.newton.core.domain.models.about_us.Community
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    onCommunityDetailsClick: (Community) -> Unit,
    communitiesViewModel: CommunitiesViewModel,
    executiveViewModel: ExecutiveViewModel,
    clubBioViewModel: ClubBioViewModel
) {
    val uiState by communitiesViewModel.uiState.collectAsStateWithLifecycle()
    val clubBioUiState by clubBioViewModel.uiState.collectAsState()
    val executiveUiState by executiveViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val communityName = "Meru Science Innovators Club"
    var isAboutExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        communitiesViewModel.uiEvents.collectLatest { event ->
            when (event) {
                is CommunityUiEvent.Effect.ShowSnackbar -> {
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Retry",
                        duration = SnackbarDuration.Long
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        communitiesViewModel.onEvent(CommunityUiEvent.Action.RefreshCommunities)
                    }
                }
            }
        }
    }

    DefaultScaffold(
        snackbarHostState = snackBarHostState,
        showOrbitals = true,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = communityName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {

                    ClubBioContent(
                        onReadMoreClick = { isAboutExpanded = !isAboutExpanded },
                        uiState = clubBioUiState,
                        isAboutExpanded = isAboutExpanded,
                    )

            }


            item {
                SectionHeading(
                    title = "Our Communities",
                    icon = Icons.Filled.Groups,
                )
            }

            item {
                CommunityContent(
                    uiState = uiState,
                    onCommunityDetailsClick = onCommunityDetailsClick,
                    communitiesViewModel = communitiesViewModel
                )
            }

            item {
                SectionHeading(
                    title = "Executive Team",
                    icon = Icons.Filled.Person
                )
            }

            item {
                ExecutivesSection(
                    uiState = executiveUiState
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

}