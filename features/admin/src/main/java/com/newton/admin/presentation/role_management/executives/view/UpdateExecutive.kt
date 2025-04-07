package com.newton.admin.presentation.role_management.executives.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.newton.admin.presentation.events.view.composables.AdminSuccessScreen
import com.newton.admin.presentation.role_management.executives.events.ExecutiveEvents
import com.newton.admin.presentation.role_management.executives.view.composables.AddExecutiveForm
import com.newton.admin.presentation.role_management.executives.view.composables.UsersListModalBottomSheet
import com.newton.admin.presentation.role_management.executives.viewModel.ExecutiveViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.ErrorScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateExecutiveScreen(
    executiveId: Int? = null,
    viewModel: ExecutiveViewModel,
    onEvent: (ExecutiveEvents) -> Unit
) {
    val execState by viewModel.execState.collectAsState()
    val userState by viewModel.userState.collectAsState()
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
        when {
            execState.successMessage != null -> {
                AdminSuccessScreen(
                    onFinish = {
                        onEvent.invoke(ExecutiveEvents.ToDefault)
                    },
                    text = execState.successMessage!!
                )
            }

            execState.errorMessage != null -> {
                ErrorScreen(
                    execState.errorMessage!!,
                    onRetry = { onEvent.invoke(ExecutiveEvents.AddExecutive) })
            }

            else -> {
                AddExecutiveForm(execState, onEvent)
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

