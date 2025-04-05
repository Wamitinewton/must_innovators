import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import com.newton.admin.presentation.community.events.*
import com.newton.admin.presentation.community.view.composable.*
import com.newton.admin.presentation.community.viewmodels.*
import com.newton.commonUi.composables.*
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

//                    AnimatedVisibility(
//                        visible = communityState.isEditing,
//                        enter = fadeIn() + expandVertically(),
//                        exit = fadeOut() + shrinkVertically()
//                    ) {
//                        IconButton(
//                            onClick = {
//                                onEvent.invoke(UpdateCommunityEvent.IsEditingChange(false))
//                            }
//                        ) {
//                            Icon(Icons.Default.Save, "Save")
//                        }
//                    }
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
