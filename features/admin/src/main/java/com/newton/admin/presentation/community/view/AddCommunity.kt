import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Webhook
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.community.events.CommunityEvent
import com.newton.admin.presentation.community.view.composable.CommunitySection
import com.newton.admin.presentation.community.view.composable.LeadershipSelectField
import com.newton.admin.presentation.community.view.composable.SessionDialog
import com.newton.admin.presentation.community.view.composable.SessionItem
import com.newton.admin.presentation.community.view.composable.SocialDialog
import com.newton.admin.presentation.community.view.composable.SocialItem
import com.newton.admin.presentation.community.viewmodels.CommunityViewModel
import com.newton.admin.presentation.role_management.executives.view.composables.UsersListModalBottomSheet
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import com.newton.common_ui.ui.CustomButton
import com.newton.common_ui.ui.CustomCard
import java.text.SimpleDateFormat
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
    val scrollState = rememberScrollState()
    val datePickerState = rememberDatePickerState()
    val sessions = addCommunityState.sessions
    val socials = addCommunityState.socials

    LaunchedEffect(key1=usersState.isLoading) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Community Information",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Community Name
                    OutlinedTextField(
                        value = addCommunityState.name,
                        onValueChange = {
                            onEvent.invoke(CommunityEvent.NameChanged(it))
                        },
                        label = { Text("Community Name") },
                        leadingIcon = { Icon(Icons.Default.Group, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        singleLine = true,
                        supportingText = {
                            addCommunityState.errors["name"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                        }
                    )
                    OutlinedTextField(
                        value = addCommunityState.dateFounded,
                        onValueChange = {
                            onEvent.invoke(CommunityEvent.DateFoundedChanged(it))
                        },
                        label = { Text("Date Founded") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                onEvent.invoke(CommunityEvent.ShowDatePicker(true))
                            }) {
                                Icon(
                                    Icons.Default.CalendarToday,
                                    contentDescription = "Select Date"
                                )
                            }
                        },
                        readOnly = true,
                        supportingText = {
                            addCommunityState.errors["date"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                        }
                    )
                    OutlinedTextField(
                        value = addCommunityState.description,
                        onValueChange = {
                            onEvent.invoke(CommunityEvent.DescriptionChanged(it))
                        },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        supportingText = {
                            addCommunityState.errors["description"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                        }
                    )
                }
            }
            CustomCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Leadership",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    LeadershipSelectField(
                        label = "Lead",
                        value = addCommunityState.lead,
                        onClick = {
                            onEvent.invoke(CommunityEvent.CurrentRoleSelectionChange("lead"))
                            onEvent.invoke(CommunityEvent.ShowBottomSheet(true))
                        }
                    )
                    addCommunityState.errors["lead"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    LeadershipSelectField(
                        label = "Co-Lead",
                        value = addCommunityState.coLead,
                        onClick = {
                            onEvent.invoke(CommunityEvent.CurrentRoleSelectionChange("co-lead"))
                            onEvent.invoke(CommunityEvent.ShowBottomSheet(true))
                        }
                    )
                    addCommunityState.errors["colead"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    LeadershipSelectField(
                        label = "Secretary",
                        value = addCommunityState.secretary,
                        onClick = {
                            onEvent.invoke(CommunityEvent.CurrentRoleSelectionChange("secretary"))
                            onEvent.invoke(CommunityEvent.ShowBottomSheet(true))
                        })
                    addCommunityState.errors["secretary"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            }
            // sessions
            CommunitySection(
                title = "Meeting Sessions",
                icon = Icons.Default.Schedule,
                trailing = {
                    Button(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        onClick = {
                            onEvent.invoke(CommunityEvent.SessionToEdit(null))
                            onEvent.invoke(CommunityEvent.ShowAddSession(true))
                        }
                    ) {
                        Row {
                            Icon(Icons.Default.Add, contentDescription = null)

                            Text("Add")
                        }
                    }
                }
            ) {
                if (sessions.isEmpty()) {
                    Text(
                        text = "No scheduled sessions",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                } else {
                    sessions.forEachIndexed { index, session ->
                        SessionItem(
                            session = session,
                            isEditing = true,
                            onEditClick = {
                                onEvent.invoke(CommunityEvent.SessionToEdit(session))
                                onEvent.invoke(CommunityEvent.ShowAddSession(true))
                            },
                            onDeleteClick = {
                                val newSessions = sessions.toMutableList()
                                newSessions.removeAt(index)
                                onEvent.invoke(CommunityEvent.SessionsChanged(newSessions))
                            }
                        )
                        if (index < sessions.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
                addCommunityState.errors["sessions"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }

            CommunitySection(
                title = "Contact Information",
                icon = Icons.Outlined.Contacts,
            ) {
                OutlinedTextField(
                    value = addCommunityState.email,
                    onValueChange = {
                        onEvent.invoke(CommunityEvent.EmailCHanged(it))
                    },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = addCommunityState.phone,
                    onValueChange = {
                        onEvent.invoke(CommunityEvent.PhoneChanged(it))
                    },
                    label = { Text("Phone") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }

            CommunitySection(
                title = "Community Socials",
                icon = Icons.Outlined.Webhook,
                trailing = {
                    Button(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        onClick = {
                            onEvent.invoke(CommunityEvent.SocialToEditChange(null))
                            onEvent.invoke(CommunityEvent.ShowAddSocialDialog(true))
                        }
                    ) {
                        Row {
                            Icon(Icons.Default.Add, contentDescription = null)

                            Text("Add")
                        }
                    }
                }
            ) {
                if (socials.isEmpty()) {
                    Text(
                        text = "There is No socials associated to this community",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                } else {
                    socials.forEachIndexed { index, social ->
                        SocialItem(
                            social = social,
                            onEditClick = {
                                onEvent.invoke(CommunityEvent.SocialToEditChange(social))
                                onEvent.invoke(CommunityEvent.ShowAddSocialDialog(true))
                            },
                            onDeleteClick = {
                                val newSocial = socials.toMutableList()
                                newSocial.removeAt(index)
                                onEvent.invoke(CommunityEvent.SocialsChanged(newSocial))
                            }
                        )
                        if (index < socials.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
            CustomCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Tools & Technologies",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Tools used
                    OutlinedTextField(
                        value = addCommunityState.toolsText,
                        onValueChange = {
                            onEvent.invoke(CommunityEvent.ToolsChanged(it))
                        },
                        label = { Text("Tools Used (comma separated)") },
                        leadingIcon = { Icon(Icons.Default.Build, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        supportingText = { Text("Example: Git, Docker, Jira") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                onClick = {
                    onEvent.invoke(CommunityEvent.AddCommunity)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                Text("Add Community")
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