import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.newton.admin.presentation.community.events.UpdateCommunityEffect
import com.newton.admin.presentation.community.events.UpdateCommunityEvent
import com.newton.admin.presentation.community.view.composable.SessionDialog
import com.newton.admin.presentation.community.view.composable.UpdateCommunityCard
import com.newton.admin.presentation.community.viewmodels.CommunitySharedViewModel
import com.newton.admin.presentation.community.viewmodels.UpdateCommunityViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.OopsError
import com.newton.common_ui.ui.LoadingDialog
import com.newton.common_ui.ui.LoadingIndicator
import com.newton.core.domain.models.admin_models.AddCommunityRequest
import com.newton.core.domain.models.admin.Session
import com.newton.core.domain.models.admin.Socials


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
                },
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
                    community, onEvent,
                    isEditing = communityState.isEditing,
                    communityState = communityState
                )
            }
        }
    }
}