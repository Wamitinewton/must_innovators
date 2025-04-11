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
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import com.newton.admin.presentation.community.events.*
import com.newton.admin.presentation.community.view.composable.*
import com.newton.admin.presentation.community.viewmodels.*
import com.newton.commonUi.ui.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCommunityScreen(
    sharedViewModel: CommunitySharedViewModel,
    viewModel: UpdateCommunityViewModel,
    onEvent: (UpdateCommunityEvent) -> Unit
) {
    val updateCommunityEffect by sharedViewModel.uiState.collectAsState()
    val communityState by viewModel.updateCommunityState.collectAsState()
    DisposableEffect(Unit) {
        onDispose {
            sharedViewModel.clearSelectedCommunity()
        }
    }

    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (communityState.isEditing) "Edit Community" else "Community Details",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        onEvent.invoke(UpdateCommunityEvent.IsEditingChange(!communityState.isEditing))
                    }) {
                        Icon(
                            if (communityState.isEditing) Icons.Default.Close else Icons.Default.Edit,
                            contentDescription = if (communityState.isEditing) "Cancel" else "Edit"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (communityState.isEditing) {
                FloatingActionButton(
                    onClick = {
                        onEvent.invoke(UpdateCommunityEvent.SessionToEdit(null))
                        onEvent.invoke(UpdateCommunityEvent.ShowAddSession(true))
                    },
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Session",
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        when (updateCommunityEffect) {
            is UpdateCommunityEffect.Error -> {
                OopsError(errorMessage = (updateCommunityEffect as UpdateCommunityEffect.Error).message)
            }

            UpdateCommunityEffect.Initial -> {
                LoadingDialog()
            }

            is UpdateCommunityEffect.Success -> {
                val community =
                    (updateCommunityEffect as UpdateCommunityEffect.Success).community
                UpdateCommunityCard(
                    community,
                    onEvent,
                    isEditing = communityState.isEditing,
                    communityState = communityState
                )
            }
        }
    }
}
