import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.newton.admin.presentation.community.events.CommunityEvent
import com.newton.admin.presentation.community.view.composable.AddCommunityForm
import com.newton.admin.presentation.community.view.composable.SessionDialog
import com.newton.admin.presentation.community.view.composable.SocialDialog
import com.newton.admin.presentation.community.viewmodels.CommunityViewModel
import com.newton.admin.presentation.events.view.composables.AdminSuccessScreen
import com.newton.admin.presentation.role_management.executives.view.composables.UsersListModalBottomSheet
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import com.newton.common_ui.ui.ErrorScreen
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommunityScreen(
    onEvent: (CommunityEvent) -> Unit,
    viewModel: CommunityViewModel,
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
                    }, text = "Added Community Successfully"
                )
            }

            addCommunityState.uploadError != null -> {
                ErrorScreen(
                    addCommunityState.uploadError!!,
                    onRetry = { onEvent.invoke(CommunityEvent.AddCommunity) })
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