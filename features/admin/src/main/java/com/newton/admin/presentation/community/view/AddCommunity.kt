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
package com.newton.admin.presentation.community.view

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.newton.admin.presentation.community.events.*
import com.newton.admin.presentation.community.view.composable.*
import com.newton.admin.presentation.community.viewmodels.*
import com.newton.admin.presentation.events.view.composables.*
import com.newton.admin.presentation.roleManagement.executives.view.composables.*
import com.newton.commonUi.ui.*
import java.text.*
import java.time.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommunityScreen(
    onEvent: (CommunityEvent) -> Unit,
    viewModel: CommunityViewModel
) {
    val addCommunityState by viewModel.communityState.collectAsState()
    val usersState by viewModel.userState.collectAsState()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val currentDate = LocalDateTime.now().toLocalDate().atStartOfDay()
                val currentMillis = currentDate.toInstant(ZoneOffset.UTC).toEpochMilli()
                return utcTimeMillis <= currentMillis
            }
        }
    )
    val sessions = addCommunityState.sessions
    val socials = addCommunityState.socials

    LaunchedEffect(key1 = usersState.isLoading) {
        if (usersState.users.isEmpty()) {
            onEvent.invoke(CommunityEvent.LoadUsers(true))
        }
    }

    DefaultScaffold(
        topBar = {
            MeruInnovatorsAppBar("Add New Community")
        },
        isLoading = addCommunityState.isLoading
    ) {
        when {
            addCommunityState.isSuccess -> {
                AdminSuccessScreen(
                    onFinish = {
                        onEvent.invoke(CommunityEvent.ToDefault)
                    },
                    text = "Added Community Successfully"
                )
            }

            addCommunityState.uploadError != null -> {
                ErrorScreen(
                    addCommunityState.uploadError!!,
                    onRetry = { onEvent.invoke(CommunityEvent.AddCommunity) }
                )
            }

            else -> {
                AddCommunityForm(addCommunityState, onEvent)
            }
        }

        if (addCommunityState.showAddSessionDialog) {
            SessionDialog(
                session = addCommunityState.sessionToEdit,
                onDismiss = {
                    onEvent.invoke(CommunityEvent.ShowAddSession(false))
                },
                onSave = { session ->
                    val newSessions = sessions.toMutableList()
                    if (addCommunityState.sessionToEdit == null) {
                        newSessions.add(session)
                    } else {
                        val index = newSessions.indexOf(addCommunityState.sessionToEdit)
                        if (index != -1) {
                            newSessions[index] = session
                        }
                    }
                    onEvent.invoke(CommunityEvent.SessionsChanged(newSessions))
                    onEvent.invoke(CommunityEvent.ShowAddSession(false))
                    onEvent.invoke(CommunityEvent.SessionToEdit(null))
                }
            )
        }
        if (addCommunityState.showAddSocialDialog) {
            SocialDialog(
                social = addCommunityState.socialToEdit,
                onDismiss = {
                    onEvent.invoke(CommunityEvent.ShowAddSocialDialog(false))
                },
                onSave = { social ->
                    val newSocial = socials.toMutableList()
                    if (addCommunityState.socialToEdit == null) {
                        newSocial.add(social)
                    } else {
                        val index = newSocial.indexOf(addCommunityState.socialToEdit)
                        if (index != -1) {
                            newSocial[index] = social
                        }
                    }
                    onEvent.invoke(CommunityEvent.SocialsChanged(newSocial))
                    onEvent.invoke(CommunityEvent.ShowAddSocialDialog(false))
                    onEvent.invoke(CommunityEvent.SocialToEditChange(null))
                }
            )
        }
        if (addCommunityState.showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    onEvent.invoke(CommunityEvent.ShowDatePicker(false))
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = Date(millis)
                                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                val formattedDate = format.format(date)

                                onEvent.invoke(CommunityEvent.DateFoundedChanged(formattedDate))
                            }
                            onEvent.invoke(CommunityEvent.ShowDatePicker(false))
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        onEvent.invoke(CommunityEvent.ShowDatePicker(false))
                    }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        if (addCommunityState.showBottomSheet) {
            val users = viewModel.getSearchedUser()
            UsersListModalBottomSheet(
                users = users,
                onUserSelected = { user ->
                    when (addCommunityState.currentRoleSelection) {
                        "lead" -> {
                            onEvent.invoke(CommunityEvent.LeadChanged(user.name))
                            onEvent.invoke(CommunityEvent.LeadIdChanged(user.id))
                        }

                        "co-lead" -> {
                            onEvent.invoke(CommunityEvent.CoLeadChanged(user.name))
                            onEvent.invoke(CommunityEvent.CoLeadIdChanged(user.id))
                        }

                        "secretary" -> {
                            onEvent.invoke(CommunityEvent.SecretaryChanged(user.name))
                            onEvent.invoke(CommunityEvent.SecretaryIdChanged(user.id))
                        }
                    }
                    onEvent.invoke(CommunityEvent.ShowBottomSheet(false))
                },
                searchQuery = usersState.searchQuery,
                onSearchQueryChange = { onEvent.invoke(CommunityEvent.SearchQuery(it)) },
                onSearchClicked = { onEvent.invoke(CommunityEvent.SearchQuery("")) },
                isLoading = usersState.isLoading,
                errorMessage = usersState.getUsersError,
                onErrorRetry = { onEvent.invoke(CommunityEvent.LoadUsers(true)) },
                onDismiss = { onEvent.invoke(CommunityEvent.ShowBottomSheet(false)) }
            )
        }
    }
}
