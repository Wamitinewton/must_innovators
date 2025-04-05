package com.newton.admin.presentation.roleManagement.executives.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.newton.admin.presentation.events.view.management.*
import com.newton.admin.presentation.roleManagement.executives.events.*
import com.newton.admin.presentation.roleManagement.executives.view.composables.*
import com.newton.admin.presentation.roleManagement.executives.viewModel.*
import com.newton.commonUi.composables.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateExecutiveScreen(
    navController: NavController,
    executiveId: Int? = null,
    viewModel: ExecutiveViewModel,
    onEvent: (ExecutiveEvents) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val execState by viewModel.execState.collectAsState()
    val userState by viewModel.userState.collectAsState()
    val positions: List<String> =
        listOf(
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
            modifier =
            Modifier
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
                    modifier =
                    Modifier
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
            onDismiss = { onEvent.invoke(ExecutiveEvents.ShowBottomSheet(false)) }
        )
    }
}
