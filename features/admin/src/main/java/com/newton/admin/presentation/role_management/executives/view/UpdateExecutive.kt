package com.newton.admin.presentation.role_management.executives.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.newton.admin.presentation.role_management.executives.events.ExecutiveEvents
import com.newton.admin.presentation.role_management.executives.view.composables.PositionDropdown
import com.newton.admin.presentation.role_management.executives.view.composables.SelectedUserCard
import com.newton.admin.presentation.role_management.executives.view.composables.UsersListModalBottomSheet
import com.newton.admin.presentation.role_management.executives.viewModel.ExecutiveViewModel
import com.newton.common_ui.composables.DefaultScaffold


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateExecutiveScreen(
    navController: NavController,
    executiveId: Int? = null,
    viewModel: ExecutiveViewModel,
    onEvent: (ExecutiveEvents) -> Unit
) {
    val execState by viewModel.execState.collectAsState()
    val userState by viewModel.userState.collectAsState()
    val positions: List<String> = listOf(
        "Chair person",
        "Vice chair person",
        "coordinator",
        "Club Secretary",
        "Vice Secretary",
        "Treasurer",
        "Social media manager"
    )
    val modalBottomSheetState = rememberModalBottomSheetState()
    LaunchedEffect(key1 = execState.showBottomSheet) {
        if (userState.users.isEmpty()) {
            onEvent.invoke(ExecutiveEvents.LoadUsers)
        }
    }


    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (executiveId != null) "Update Executive" else "Add Executive") },
                actions = {
                    IconButton(onClick = { onEvent.invoke(ExecutiveEvents.ShowBottomSheet(true)) }) {
                        Icon(Icons.Default.PersonSearch, contentDescription = "Select User")
                    }
                }
            )
        },
        isLoading = execState.isLoading
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = execState.bio,
                    onValueChange = { onEvent.invoke(ExecutiveEvents.BioChanged(it)) },
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Description,
                            contentDescription = null
                        )
                    },
                    minLines = 3,
                    maxLines = 5,
                    supportingText = {
                        execState.errors["bio"]?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
            }

            item {
                Text(
                    text = "Lead position",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )
                PositionDropdown(
                    positions = positions,
                    onEvent = onEvent,
                    state = execState
                )
            }
            execState.selectedUser?.let { user ->
                item {
                    SelectedUserCard(user = user)
                    execState.errors["user"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onEvent.invoke(ExecutiveEvents.AddExecutive)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = execState.bio.isNotEmpty() && execState.selectedUser != null && execState.position != null
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (executiveId != null) "UPDATE EXECUTIVE" else "ADD EXECUTIVE",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

    if (execState.showBottomSheet) {
        val users = viewModel.getSearchedUser()
        UsersListModalBottomSheet(
            users = users,
            onUserSelected = { user ->
                onEvent.invoke(ExecutiveEvents.SelectedUserChange(user))
                onEvent.invoke(ExecutiveEvents.ShowBottomSheet(false))
            },
            searchQuery = userState.searchQuery,
            onSearchQueryChange = { onEvent.invoke(ExecutiveEvents.SearchQuery(it)) },
            onSearchClicked = { onEvent.invoke(ExecutiveEvents.SearchQuery("")) },
            isLoading = userState.isLoading,
            errorMessage = userState.getUsersError,
            onErrorRetry = { onEvent.invoke(ExecutiveEvents.LoadUsers) },
            onDismiss = {onEvent.invoke(ExecutiveEvents.ShowBottomSheet(false))}
        )
    }
}

