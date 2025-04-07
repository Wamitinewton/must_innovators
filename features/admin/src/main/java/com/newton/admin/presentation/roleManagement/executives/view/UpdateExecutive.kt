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
package com.newton.admin.presentation.roleManagement.executives.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.newton.admin.presentation.events.view.composables.AdminSuccessScreen
import com.newton.admin.presentation.roleManagement.executives.events.ExecutiveEvents
import com.newton.admin.presentation.roleManagement.executives.view.composables.UsersListModalBottomSheet
import com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel
import com.newton.admin.presentation.roleManagement.executives.view.composables.AddExecutiveForm
import com.newton.commonUi.composables.DefaultScaffold
import com.newton.commonUi.ui.ErrorScreen


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

